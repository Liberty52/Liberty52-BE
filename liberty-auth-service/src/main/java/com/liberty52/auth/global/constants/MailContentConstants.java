package com.liberty52.auth.global.constants;

public class MailContentConstants {

    public static final String PASSWORD_MAIL_CONTENT_HTML =
            "<table style=\"width : 100%%; margin : \n" +
                    "0; background-color: #eee;\">\n" +
                    "\n" +
                    "    <tbody>\n" +
                    "        <tr>\n" +
                    "            <td height=\"50\"></td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td>\n" +
                    "                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%%\">\n" +
                    "                    <tbody>\n" +
                    "                        <tr>\n" +
                    "                            <td>\n" +
                    "                                <table style=\"margin:0 auto;background:#fff;max-width:600px;width:100%%\"\n" +
                    "                                cellpadding=\"0\" cellspacing=\"0\" border=\"0\"\n" +
                    "                                >\n" +
                    "                                    <tbody>\n" +
                    "                                        <tr>\n" +
                    "                                            <td width=\"5%%\" style=\"border-bottom:1px solid #eee\"></td>\n" +
                    "                                \n" +
                    "                                            <td style=\"border-bottom:1px solid #eee\">\n" +
                    "                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%%\">\n" +
                    "                                                    <tbody>\n" +
                    "                                                        <tr>\n" +
                    "                                                            <th height=\"40\"></th>\n" +
                    "                                                        </tr> \n" +
                    "                                                        <tr>\n" +
                    "                                                            <td style=\"text-align:center;color:#757575;font-size:24px;\">\n" +
                    "                                                                <h3>Liberty52</h3>\n" +
                    "                                                            </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                        <tr>\n" +
                    "                                                            <th height=\"20\"></th>\n" +
                    "                                                        </tr>\n" +
                    "                                                        <tr>\n" +
                    "                                                            <!-- Main Text -->\n" +
                    "                                                            <td style=\"text-align:center;font-size:22px;color:#757575\">\n" +
                    "                                                                <h3>비밀번호 변경 안내</h3>\n" +
                    "                                                            </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                        <tr>\n" +
                    "                                                            <th height=\"20\"></th>\n" +
                    "                                                        </tr>\n" +
                    "                                                    </tbody>\n" +
                    "                                                </table>\n" +
                    "                                         \n" +
                    "                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%%\">\n" +
                    "                                                    <tbody>\n" +
                    "                                                        <tr height=\"20\">\n" +
                    "                                                            <td></td>\n" +
                    "                                                        </tr>\n" +
                    "                                                        <tr>\n" +
                    "                                                            <td style=\"font-size:14px;color:#777;text-align:center\">\n" +
                    "                                                                <div style=\"padding-bottom:15px\">\n" +
                    "                                                                    <!-- sub text -->\n" +
                    "                                                                    안녕하세요. %s 님. <br><br>\n" +
                    "                                                                    본 메일은 비밀번호 재설정을 위해 Liberty52에서 발송하는 메일입니다.<br><br>\n" +
                    "                                                                    본인이 요청한 메일이 아니라면 개인정보 보호를 위해 비밀번호를 재설정해주세요.<br><br>\n" +
                    "                                                                    비밀번호를 다시 설정하려면 '비밀번호 재설정' 링크를 클릭해주세요.<br><br>\n" +
                    "                                                                    아래 링크는 약 10분 간 유효한 링크입니다. 10분이 지났다면, 다시 요청해주세요.<br><br>\n" +
                    "                                                                </div>\n" +
                    "                                                            </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                        <tr height=\"25\">\n" +
                    "                                                            <td></td>\n" +
                    "                                                        </tr>\n" +
                    "                                                    </tbody>\n" +
                    "                                                </table>\n" +
                    "                                            </td>\n" +
                    "                                          \n" +
                    "                                            <td width=\"5%%\" style=\"border-bottom:1px solid #eee\"></td>\n" +
                    "                                        </tr>\n" +
                    "\n" +
                    "\n" +
                    "                                        <tr height=\"50\"><td></td></tr>\n" +
                    "                                        <tr>\n" +
                    "                                            <td width=\"5%%\">&nbsp;&nbsp;</td>\n" +
                    "                                            <td>\n" +
                    "                                                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%%\">\n" +
                    "                                                    <tbody>\n" +
                    "                                                        <tr>\n" +
                    "                                                            <td style=\"text-align:center\">\n" +
                    "                                                                <a href=\"%s\" style=\"padding:12px 16px;width:270px;color:#ffffff;background-color:#212121;border-color:#00b8ff;display:inline-block;text-decoration:none;font-size:20px\">\n" +
                    "                                                                    비밀번호 재설정\n" +
                    "                                                                </a>\n" +
                    "                                                            </td>\n" +
                    "                                                        </tr>\n" +
                    "                                                        <tr height=\"50\"><td></td></tr>\n" +
                    "                                                    </tbody>\n" +
                    "                                                </table>\n" +
                    "                                            </td>\n" +
                    "                                            <td width=\"5%%\"></td>\n" +
                    "                                        </tr>\n" +
                    "                                    </tbody>\n" +
                    "                                </table>\n" +
                    "                            </td>\n" +
                    "                        </tr>\n" +
                    "                    </tbody>\n" +
                    "                </table>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "    </tbody>\n" +
                    "    <tfoot style=\"background:#eee;display:block;width:600px;margin:0 auto\">\n" +
                    "        <tr style=\"display:block;text-align:center\">\n" +
                    "            <th style=\"font-size:12px;font-weight:normal;color:#767676;padding:10px 0 50px;display:block\">@Liberty52</th>\n" +
                    "        </tr>\n" +
                    "\n" +
                    "    </tfoot>\n" +
                    "</table>";
}
