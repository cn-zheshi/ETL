<?xml version= "1.0" encoding= "gb2312"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:jw="http://jw.nju.edu.cn/schema">
    <xsl:output method="xml" encoding="gb2312" />
    <xsl:template match="/">
        <xsl:apply-templates />
    </xsl:template>
    <xsl:template match="jw:Choices">
        <Choices>
            <xsl:for-each select="jw:choice">
                <choice>
                    <Sno>
                        <xsl:value-of select="jw:sid" />
                    </Sno>
                    <Cno>
                        <xsl:value-of select="jw:cid" />
                    </Cno>
                    <Grd>
                        <xsl:value-of select="jw:score" />
                    </Grd>
                </choice>
            </xsl:for-each>
        </Choices>
    </xsl:template>
</xsl:stylesheet>