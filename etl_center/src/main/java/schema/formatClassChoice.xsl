<?xml version= "1.0" encoding= "gb2312"?>
<xsl:stylesheet version= "1.0" xmlns:xsl= "http://www.w3.org/1999/XSL/Transform">
    <xsl:output method= "xml" encoding= "gb2312"/>
    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>
    <xsl:template match="Choices">
        <Choices>
            <xsl:for-each select="choice">
                <choice>
                    <sid>
                        <xsl:value-of select="学生编号"/>
                        <xsl:value-of select="学号"/>
                        <xsl:value-of select="Sno"/>
                    </sid>
                    <xsl:choose>
                        <xsl:when test="./Cno=''">
                            <cid>
                                <xsl:value-of select="课程编号"/>
                            </cid>
                        </xsl:when>
                        <xsl:when test="./Cno!=''">
                            <cid>
                                <xsl:value-of select="Cno"/>
                            </cid>
                        </xsl:when>
                    </xsl:choose>
                    <score>
                        <xsl:value-of select="成绩"/>
                        <xsl:value-of select="得分"/>
                        <xsl:value-of select="Grd"/>
                    </score>
                </choice>
            </xsl:for-each>
        </Choices>
    </xsl:template>
</xsl:stylesheet>