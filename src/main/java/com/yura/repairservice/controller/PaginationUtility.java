package com.yura.repairservice.controller;

import com.yura.repairservice.exception.InvalidPaginationException;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

public interface PaginationUtility {

    default <T> ModelAndView paginate(Integer currentPage, Integer recordsPerPage,
                                      String view, long entries, List<T> entities) {

        int numberOfPages = (int) Math.ceil((double) entries / recordsPerPage);
        ModelAndView modelAndView = new ModelAndView(view);

        modelAndView.addObject("entities", entities);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("recordsPerPage", recordsPerPage);
        modelAndView.addObject("numberOfPages", numberOfPages);

        return modelAndView;
    }

    default void paginationValidate(Integer currentPage, Integer recordsPerPage) {
        if (Objects.isNull(currentPage) || currentPage < 0
                || Objects.isNull(recordsPerPage) || recordsPerPage < 0) {
            throw new InvalidPaginationException("Invalid pagination parameters");
        }
    }
}
