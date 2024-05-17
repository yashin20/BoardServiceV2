package project.boardserviceV2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.boardserviceV2.dto.PostInfoDto;
import project.boardserviceV2.service.PostService;


@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    @GetMapping("/")
    public String home(@PageableDefault(page = 1) Pageable pageable, Model model) {
        // pageable.getPageNumber()는 1부터 시작하도록 설정
        int currentPage = pageable.getPageNumber() - 1; // Spring Data JPA의 0 기반 페이지 인덱스로 조정
        Page<PostInfoDto> posts = postService.getPostList(PageRequest.of(currentPage, pageable.getPageSize(), pageable.getSort()));

        model.addAttribute("postList", posts.getContent());
        model.addAttribute("page", posts);

        /**
         * 마지막 페이지 : 21 (사용자 기준)
         * 사용자 기준 페이지 : 15
         * 서버 기준 currentPage : 14
         * blockLimit : 5
         * currentBlock : 14 / 5 = 2 (블록 2)
         * startPage : 2 * 5 + 1 = 11 (사용자 기준 : 1부터 시작)
         * endPage : min((2 + 1) * 5, 21) = 15 (사용자 기준)
         */

        int blockLimit = 5; //페이지 개수 설정 (5)
        int currentBlock = currentPage / blockLimit; //현재 페이지가 속한 페이지 블록(0 부터 시작)
        int startPage = currentBlock * blockLimit + 1; //블록 시작 페이지 (사용자 기준)
        int endPage = Math.min((currentBlock + 1) * blockLimit, posts.getTotalPages()); // 블록의 끝 페이지 (사용자 기준)

        model.addAttribute("currentBlock", currentBlock);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "index";
    }
}
