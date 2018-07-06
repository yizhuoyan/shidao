package com.yizhuoyan.shidao.platform.function;

import com.yizhuoyan.shidao.platform.dto.UserContext;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserCommonFunction {

/**
 * 用户登录
 *
 * @param account  账户
 * @param password 密码
 * @return 用户上下文
 * @throws Exception
 */
UserContext login(String account, String password) throws Exception;

/**
 * 用户修改密码
 *
 * @param userId             用户id
 * @param oldPassword        旧密码
 * @param newPassword        新密码
 * @param newPasswordConfirm 新密码确认
 * @throws Exception
 */
void modifyPassword(String userId, String oldPassword, String newPassword, String newPasswordConfirm) throws Exception;

/**
 * 第一个登陆修改密码
 * @param userId
 * @param newPassword
 * @param newPasswordConfirm
 * @throws Exception
 */
void firstLoginModifyPassword(String userId, String newPassword, String newPasswordConfirm) throws Exception;


}
