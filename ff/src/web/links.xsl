<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <xsl:template name="links">
        <div id="links">
            <a href="/">последние</a>
            ::
            <a href="/?best">лучшие</a>
            ::
            <a href="/liked.xml">понравившиеся</a>
            ::
            <a href="/from.xml">мои</a>
            ::
            <a href="send.xml">отправить</a>
        </div>
        <div class="blank"/>
    </xsl:template>


    <xsl:template name="pages">
        <div id="links">
            <xsl:variable name="page">
                <xsl:value-of select="/page/page/."/>
            </xsl:variable>

            <xsl:choose>
                <xsl:when test="/page/data[@id='list']/best">
                    <xsl:if test="$page > 0">
                        <a href="?page={$page -1}&amp;best">предыдущие</a>
                        ::
                    </xsl:if>
                    <a href="?page={$page +1}&amp;best">следующие</a>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:if test="$page > 0">
                        <a href="?page={$page -1}">предыдущие</a>
                        ::
                    </xsl:if>
                    <a href="?page={$page +1}&amp;">следующие</a>

                </xsl:otherwise>
            </xsl:choose>
            <div class="blank"/>
        </div>
    </xsl:template>

</xsl:stylesheet>