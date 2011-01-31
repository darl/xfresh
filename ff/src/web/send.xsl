<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <xsl:include href="main.xsl"/>
    <xsl:include href="links.xsl"/>
    <xsl:include href="header.xsl"/>

    <xsl:template name="page">
        <xsl:choose>
            <xsl:when test="//page/data[@id='addStory']/success">
                <center>
                    <div id="inner" style="width:300px; height:100px">
                        История опубликована.
                    </div>
                </center>
            </xsl:when>
            <xsl:otherwise>
                <form method="post" action="send.xml">
                    <center>
                        <div id="inner">
                            <xsl:if test="//page/data[@id='addStory']/invalid-user">
                                <font color="red">Необходимо войти в систему</font>
                            </xsl:if>

                            <table width="100%">
                                <tr>
                                    <td>Тема истории</td>
                                    <td>
                                        <input style="width:100%" type="text" name="descr"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        История
                                        <br/>
                                        <textarea name="body" WRAP="virtual" cols="40" rows="3"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" style="text-align:center">
                                        <input type="submit" value="отправить" class="button"/>
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