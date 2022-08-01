package com.task.demo2.service;

import com.task.demo2.domain.Thread;
import com.task.demo2.domain.ThreadLike;
import com.task.demo2.domain.User;
import com.task.demo2.domain.UserThread;
import com.task.demo2.dto.req.ThreadCreateReqDto;
import com.task.demo2.dto.req.ThreadUpdateReqDto;
import com.task.demo2.dto.res.CommonResponse;
import com.task.demo2.dto.res.ThreadResDto;
import com.task.demo2.repository.ThreadLikeRepository;
import com.task.demo2.repository.ThreadRepository;
import com.task.demo2.repository.UserRepository;
import com.task.demo2.repository.UserThreadRepository;
import com.task.demo2.utils.ThreadStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ThreadService {
    private final ThreadRepository threadRepository;
    private final UserRepository userRepository;
    private final ThreadLikeRepository threadLikeRepository;
    private final UserThreadRepository userThreadRepository;

    @Transactional(readOnly = true)
    public CommonResponse<List<ThreadResDto>> getThreadList(Integer page) {
        List<Thread> threads = threadRepository.findAllByStatusOrderByModifiedDateDesc(PageRequest.of(page - 1, 10, Sort.by("modifiedDate").descending()), ThreadStatus.APPROVED);
        List<ThreadResDto> threadResList = new ArrayList<>();
        for (Thread t : threads) {
            ThreadResDto res = new ThreadResDto();
            res.setId(t.getId());
            res.setTitle(t.getThreadTitle());
            res.setContent(t.getContent());
            res.setLikeCount(threadLikeRepository.countAllByThreadId(t.getId()));
            res.setCreatedDate(t.getCreatedDate());

            threadResList.add(res);
        }

        return CommonResponse.response(HttpStatus.OK.value(), "Thread 리스트 조회", threadResList);
    }

    @Transactional(readOnly = true)
    public CommonResponse<ThreadResDto> getThreadInfo(Long threadId) {
        Thread thread = threadRepository.findByIdAndStatus(threadId, ThreadStatus.APPROVED);
        if (thread == null) {
            return CommonResponse.response(HttpStatus.NOT_FOUND.value(), "Thread 를 찾을 수 없습니다.");
        }

        ThreadResDto resDto = new ThreadResDto();
        resDto.setId(threadId);
        resDto.setTitle(thread.getThreadTitle());
        resDto.setContent(thread.getContent());
        resDto.setCreatedDate(thread.getCreatedDate());
        resDto.setLikeCount(threadLikeRepository.countAllByThreadId(threadId));

        return CommonResponse.response(HttpStatus.OK.value(), "Thread 조회", resDto);
    }

    @Transactional
    public CommonResponse<?> registerThread(ThreadCreateReqDto reqDto, HttpServletRequest request) {
        String userEmailFromToken = request.getAttribute("email").toString();
        User user = userRepository.findByEmail(userEmailFromToken);
        if (user == null) {
            return CommonResponse.response(HttpStatus.NOT_FOUND.value(), "사용자가 존재하지 않습니다.");
        }

        Thread thread = Thread.builder()
                .threadTitle(reqDto.getTitle())
                .content(reqDto.getContent())
//                .coverImage(reqDto.getCoverImage())
                .viewCount(0L)
                .likeCount(0L)
                .status(ThreadStatus.WAITING)
                .build();
        threadRepository.save(thread);
        UserThread userThread = UserThread.builder().user(user).thread(thread).build();
        userThreadRepository.save(userThread);

        return CommonResponse.response(HttpStatus.OK.value(), "Thread 신청 완료");
    }

    @Transactional
    public CommonResponse<?> likeThread(Long threadId, HttpServletRequest request) {
        String userEmailFromToken = request.getAttribute("email").toString();
        User user = userRepository.findByEmail(userEmailFromToken);
        if (user == null) {
            return CommonResponse.response(HttpStatus.NOT_FOUND.value(), "사용자가 존재하지 않습니다.");
        }
        if (!threadRepository.existsByIdAndStatus(threadId, ThreadStatus.APPROVED)) {
            return CommonResponse.response(HttpStatus.NOT_FOUND.value(), "Thread 가 존재하지 않습니다.");
        }

        ThreadLike threadLike = ThreadLike.builder()
                .userId(user.getId())
                .threadId(threadId)
                .build();
        threadLikeRepository.save(threadLike);

        return CommonResponse.response(HttpStatus.OK.value(), "Thread 좋아요 완료");
    }

    @Transactional
    public CommonResponse<?> updateThread(Long threadId, ThreadUpdateReqDto reqDto, HttpServletRequest request) {
        String userEmailFromToken = request.getAttribute("email").toString();
        User user = userRepository.findByEmail(userEmailFromToken);
        if (user == null) {
            return CommonResponse.response(HttpStatus.NOT_FOUND.value(), "사용자가 존재하지 않습니다.");
        }
        Thread thread = threadRepository.findByIdAndStatus(threadId, ThreadStatus.APPROVED);
        if (thread == null) {
            return CommonResponse.response(HttpStatus.NOT_FOUND.value(), "Thread 가 존재하지 않습니다.");
        }
        thread.updateContent(reqDto.getContent());

        return CommonResponse.response(HttpStatus.OK.value(), "Thread 업데이트 완료");
    }
}
