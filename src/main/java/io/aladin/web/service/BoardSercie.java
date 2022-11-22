package io.aladin.web.service;

import io.aladin.web.entity.Board;
import io.aladin.web.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardSercie {

    @Autowired
    private BoardRepository boardRepository;

    //글 작성 처리
    public void write(Board board){

        boardRepository.save(board);
    }
    //게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable){
        return  boardRepository.findAll(pageable);
    }

    // 특정 게시글 불러오기
    public Board boardView(Integer id){
        return boardRepository.findById(id).get();
    }

    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }

    public Page<Board> boardSearchList(String SearchKeyword,Pageable pageable){
        return boardRepository.findByTitleContaining(SearchKeyword, pageable);
    }
}
