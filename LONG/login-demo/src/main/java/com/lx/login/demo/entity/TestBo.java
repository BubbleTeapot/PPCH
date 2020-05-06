package com.lx.login.demo.entity;

import com.lx.login.demo.comm.ResponseMsg;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author longxin
 * @description: TODO
 * @date 2020/4/29 11:59
 */
@Slf4j
public class TestBo implements Serializable {
    private static final long serialVersionUID = 6397403696919979717L;
    private static final String MESSAGE = ResponseMsg.PARAM_IS_NULL.getResultMessage();


    @NotNull(message = "存在空参数")
    private String name;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TestBo{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
