/*
 * Copyright 2016 Cnlyml
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.lyml.summer.base.entity.pager;

import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * Page分页
 * @ClassName: Pager
 * @author: cnlyml
 * @date: 2016年7月17日 下午6:25:40
 */
public class Pager extends RowBounds implements Pageable {
    public final int DEFAULT_PAGESIZE = 10;
    private int pageNumber;
    private int pageSize;
    private Sort sort;

    public Pager() {
        this.pageNumber = 0;
        this.pageSize = DEFAULT_PAGESIZE;
        this.sort = null;
    }

    public Pager(int pageNumber, int pageSize) {
        super(pageNumber * pageSize, pageSize);
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sort = null;
        if (0 > pageNumber) {
            pageNumber = 0;
        }
        if (0 >= pageSize) {
            pageSize = DEFAULT_PAGESIZE;
        }
    }

    public Pager(int pageNumber, int pageSize, Sort sort) {
        super(pageNumber * pageSize, pageSize);
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sort = sort;
        if (0 > pageNumber) {
            pageNumber = 0;
        }
        if (0 >= pageSize) {
            pageSize = DEFAULT_PAGESIZE;
        }
    }

    public Pager(int pageNumber, int pageSize, Direction direction,
                 String... properties) {
        this(pageNumber, pageSize, new Sort(direction, properties));
    }

    @Override
    public Pageable first() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    public void setPageNumber(int pageNumber) {
        if (pageNumber < 0) {
            pageNumber = 0;
        }
        this.pageNumber = pageNumber;
    }

    public void setPageSize(int pageSize) {
        if (pageSize < 0) {
            pageSize = DEFAULT_PAGESIZE;
        }
        this.pageSize = pageSize;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    @Override
    public int getOffset() {
        return pageNumber * pageSize;
    }

    @Override
    public int getLimit() {
        return pageSize;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof PageRequest)) {
            return false;
        }

        PageRequest that = (PageRequest) obj;

        boolean pageEqual = this.pageNumber == that.getPageNumber();
        boolean sizeEqual = this.pageSize == that.getPageSize();

        boolean sortEqual = this.sort == null ? that.getSort() == null
                : this.sort.equals(that.getSort());

        return pageEqual && sizeEqual && sortEqual;
    }

    @Override
    public int hashCode() {

        int result = 17;

        result = 31 * result + pageNumber;
        result = 31 * result + pageSize;
        result = 31 * result + (null == sort ? 0 : sort.hashCode());

        return result;
    }

    @Override
    public boolean hasPrevious() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Pageable next() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        // TODO Auto-generated method stub
        return null;
    }
}
