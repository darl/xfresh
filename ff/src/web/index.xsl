<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <xsl:include href="main.xsl"/>
    <xsl:include href="links.xsl"/>
    <xsl:include href="header.xsl"/>

    <xsl:template name="page">
        <xsl:call-template name="show-stories"/>
        <xsl:call-template name="pages"/>
    </xsl:template>


</xsl:stylesheet>