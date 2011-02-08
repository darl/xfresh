<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <xsl:include href="main.xsl"/>
    <xsl:include href="links.xsl"/>
    <xsl:include href="header.xsl"/>

    <xsl:template name="page">
        <center>
            <xsl:call-template name="show-story"/>
            <xsl:call-template name="show-likedby"/>
        </center>
    </xsl:template>

    <xsl:template name="show-likedby">
        <div class="story">
            <xsl:choose>
                <xsl:when test="count(page/data[3]/collection/long) > 0">
                    Эта история понравилась следующим пользователям:
                    <br/>

                </xsl:when>
                <xsl:otherwise>
                    Эта история еще никому не понравилсь.
                </xsl:otherwise>
            </xsl:choose>

            <xsl:for-each select="page/data[3]/collection/long">
                <xsl:variable name="author-id">
                    <xsl:value-of select="."/>
                </xsl:variable>
                <a href="from.xml?user={$author-id}" class="likeit">
                    <xsl:value-of select="//page/data[@id='users']/user-info[uid/.=$author-id]/fio/."/>
                </a>
                <xsl:if test="not (position()=last())">
                    <xsl:text>, </xsl:text>
                </xsl:if>
            </xsl:for-each>
        </div>

    </xsl:template>

    <xsl:template name="show-story">
        <xsl:for-each select="page/data[3]/story">
            <div class="story">
                <table width="100%">
                    <tr class="descr">
                        <td>
                            <xsl:text>Тема:</xsl:text>
                            <xsl:value-of select="description/."/>
                        </td>
                        <td style="text-align: right;color:#aaa">
                            <xsl:value-of select="@date"/>
                        </td>
                    </tr>
                    <tr colspan="2" class="body">
                        <td>
                            <xsl:value-of select="body/."/>
                        </td>
                    </tr>
                    <tr class="buttomline" valigh="middle">
                        <td>
                            <xsl:variable name="story">
                                <xsl:value-of select="@id"/>
                            </xsl:variable>
                            <xsl:variable name="liked">
                                <xsl:value-of select="@liked"/>
                            </xsl:variable>

                            <xsl:choose>
                                <xsl:when test="$liked='true'">
                                    <a href="likedby.xml?story={$story}" class="likeit">
                                        <img src="like2.png"/>
                                        <xsl:text> Мне уже нравится (</xsl:text>
                                        <xsl:value-of select="@liked-by"/>
                                        <xsl:text>)</xsl:text>
                                    </a>

                                </xsl:when>
                                <xsl:otherwise>
                                    <a href="likeit.xml?story={$story}" class="likeit">
                                        <img src="like.png"/>
                                        <xsl:text> Мне нравится (</xsl:text>
                                        <xsl:value-of select="@liked-by"/>
                                        <xsl:text>)</xsl:text>
                                    </a>
                                </xsl:otherwise>
                            </xsl:choose>
                        </td>
                        <td style="text-align: right">
                            <xsl:variable name="author-id">
                                <xsl:value-of select="@author-id"/>
                            </xsl:variable>
                            <xsl:text>Отправил:</xsl:text>
                            <a href="from.xml?user={$author-id}">
                                <xsl:value-of select="//page/data[@id='users']/user-info[uid/.=$author-id]/fio/."/>
                            </a>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="blank"/>
        </xsl:for-each>
    </xsl:template>


</xsl:stylesheet>