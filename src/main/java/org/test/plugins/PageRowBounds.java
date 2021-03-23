package org.test.plugins;

import org.apache.ibatis.session.RowBounds;

/**
 * @ClassName PageRowBounds
 * @Description TODO
 * @Author zhouliansheng
 * @Date 2021/3/23 9:37
 * @Version 1.0
 **/
public class PageRowBounds extends RowBounds {
    private Long total;

    public PageRowBounds(int offset, int limit) {
        super(offset, limit);
    }

    public PageRowBounds() {
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
