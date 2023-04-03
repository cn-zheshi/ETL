<?xml version= "1.0" encoding= "gb2312"?>
<xsl:stylesheet version= "1.0" xmlns:xsl= "http://www.w3.org/1999/XSL/Transform">
    <xsl:output method= "xml" encoding= "gb2312"/>
    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>
    <xsl:template match="Students">
        <Students>
            <xsl:for-each select="student">
                <student>
                    <xsl:choose>
                        <xsl:when test="./Sno=''">
                            <id>
                                <xsl:value-of select="学号"/>
                            </id>
                        </xsl:when>
                        <xsl:when test="./Sno!=''">
                            <id>
                                <xsl:value-of select="Sno"/>
                            </id>
                        </xsl:when>
                    </xsl:choose>
                    <name>
                        <xsl:value-of select="姓名"/>
                        <xsl:value-of select="名臣"/>
                        <xsl:value-of select="Snm"/>
                    </name>
                    <sex>
                        <xsl:value-of select="性别"/>
                        <xsl:value-of select="Sex"/>
                    </sex>
                    <major>
                        <xsl:value-of select="院系"/>
                        <xsl:value-of select="专业"/>
                        <xsl:value-of select="Sde"/>
                    </major>
                </student>
            </xsl:for-each>
        </Students>
    </xsl:template>
</xsl:stylesheet>