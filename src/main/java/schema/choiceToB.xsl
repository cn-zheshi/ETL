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
                    <学生编号>
                        <xsl:value-of select="jw:sid" />
                    </学生编号>
                    <课程编号>
                        <xsl:value-of select="jw:cid" />
                    </课程编号>
                    <得分>
                        <xsl:value-of select="jw:score" />
                    </得分>
                </choice>
            </xsl:for-each>
        </Choices>
    </xsl:template>
</xsl:stylesheet>