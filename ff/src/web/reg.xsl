<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <xsl:include href="main.xsl"/>
    <xsl:include href="links.xsl"/>
    <xsl:include href="header.xsl"/>

    <xsl:template name="page">
        <xsl:choose>
            <xsl:when test="//page/data[@id='addUser']/user-info">
                <center>
                    <div id="inner">
                        Спасибо за регистрацию,
                        <xsl:value-of select="//page/data[@id='addUser']/user-info/fio/text()"/>
                    </div>
                </center>
            </xsl:when>
            <xsl:otherwise>
                <form method="post" action="reg.xml">
                    <center>
                        <div id="inner">
                            <xsl:if test="//page/data[@id='addUser']/already-exists">
                                <font color="red">Пользователь с таким именем уже существует</font>
                            </xsl:if>

                            <table>
                                <tr>
                                    <td>Имя пользователя</td>
                                    <td>
                                        <input type="text" name="user-login"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Полное имя</td>
                                    <td>
                                        <input type="text" name="user-fio"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Пароль</td>
                                    <td>
                                        <input type="password" name="user-passwd"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" style="text-align:center">
                                        <input type="submit" value="регистрация" class="button"/>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </center>
                </form>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>