package project.boardserviceV2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.boardserviceV2.dto.PostInfoDto;
import project.boardserviceV2.entity.Post;
import project.boardserviceV2.service.PostService;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    /**
     * 게시글 페이징 요청 (검색, 정렬 조건)
     * @param pageable : 페이징 조건
     * @param keyword : 검색 키워드
     * @param sort : 정렬 여부 (required = false -> 필수 파라미터가 아님)
     * @param model
     * @return
     */

    @GetMapping("/")
    public String home(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                       @RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "sort", required = false) String sort,
                       Model model) {

        //정렬 조건 설정
        if ("views".equals(sort)) {
            pageable  = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "view"));
        } else if ("createdAt".equals(sort)) {
            pageable  = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
        }

        //검색 조건 설정
        Page<Post> list = (keyword != null && !keyword.isEmpty())
                            ? postService.search(keyword, pageable)
                            : postService.pageList(pageable);
        Page<PostInfoDto> posts = list.map(PostInfoDto::new);


        model.addAttribute("posts", posts);
        model.addAttribute("sort", sort); //정렬 방식
        model.addAttribute("keyword", keyword); //검색 키워드
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber()); //이전 페이지 정보
        model.addAttribute("next", pageable.next().getPageNumber()); //다음 페이지 정보
        model.addAttribute("hasPrevious", list.hasPrevious()); //이전 페이지 존재 여부
        model.addAttribute("hasNext", list.hasNext()); //다음 페이지 존재 여부


        /** 페이지 블록 계산
         * currentPage = 5
         * User : 5 , Spring : 4
         * startPage = 1
         * endPage = 5
         * <= 1 2 3 4 5 =>
         */
        int currentPage = pageable.getPageNumber() + 1; //현재 페이지 정보(User side)
        model.addAttribute("current", currentPage);

        int blockSize = 5;
        int startPage = ((currentPage - 1) / blockSize) * blockSize + 1; //블럭 시작 페이지
        int endPage = Math.min(startPage + blockSize - 1, list.getTotalPages()); //블럭 마지막 페이지


        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "index";
    }
}
