<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>


    <xsl:template name="header">
        <div id="header">
            <img src="logo.png"/>


            <div id="login">
                <xsl:choose>
                    <xsl:when test="//page/data[@id='userInfo']/no-auth">
                        <form method="post" action="auth.xml">
                            <input type="text" name="user-login"/>
                            <input type="password" name="user-passwd"/>
                            <input type="submit" value="войти" class="button"/>
                            <br/>
                            <a href="reg.xml">регистрация</a>
                        </form>
                    </xsl:when>
                    <xsl:otherwise>
                        <i>Добро пожаловать,
                            <xsl:value-of select="//page/data[@id='userInfo']/user-info/fio"/>
                        </i>
                        <br/>
                        <a href="auth.xml">выход</a>
                    </xsl:otherwise>
                </xsl:choose>
            </div>
        </div>
    </xsl:template>

</xsl:stylesheet>