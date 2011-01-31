<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>


    <xsl:template match="/">

        <html>
            <head>
                <title>
                    <xsl:text>la fettina di felicita - Кусочек счастья</xsl:text>
                </title>

                <link type="text/css" rel="stylesheet" href="main.css"/>
            </head>
            <body>
                <xsl:call-template name="header"/>
                <xsl:call-template name="links"/>
                <xsl:call-template name="page"/>
            </body>
        </html>
    </xsl:template>

    <xsl:template name="show-stories">
        <center>
            <xsl:for-each select="page/data[3]/collection/*">
                <div class="story">
                    <table width="100%">
                        <tr class="descr">
                            <td>
                                <xsl:text>Тема: </xsl:text><xsl:value-of select="description/."/>
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

                                <a href="likeit.xml?story={$story}" class="likeit">
                                    <img src="like.png"/>
                                    <xsl:text> Мне </xsl:text>
                                    <xsl:if test="$liked='true'">
                                        <xsl:text>уже </xsl:text>
                                    </xsl:if>
                                    <xsl:text>нравится (</xsl:text>
                                    <!--<xsl:value-of select="@liked"/>-->
                                    <xsl:value-of select="@like-it"/>
                                    <xsl:text>)</xsl:text>
                                </a>
                            </td>
                            <td style="text-align: right">
                                <xsl:variable name="author-id">
                                    <xsl:value-of select="@author-id"/>
                                </xsl:variable>
                                <xsl:text>Отправил: </xsl:text>
                                <a href="from.xml?user={$author-id}">
                                    <xsl:value-of select="//page/data[@id='users']/user-info[uid/.=$author-id]/fio/."/>
                                </a>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="blank"/>
            </xsl:for-each>
        </center>
    </xsl:template>

</xsl:stylesheet>