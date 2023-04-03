<?xml version= "1.0" encoding= "gb2312"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:jw="http://jw.nju.edu.cn/schema">
    <xsl:output method="xml" encoding="gb2312" />
    <xsl:template match="/">
        <xsl:apply-templates />
    </xsl:template>
    <xsl:template match="jw:Students">
        <Students>
            <xsl:for-each select="jw:student">
                <student>
                    <学号>
                        <xsl:value-of select="jw:id" />
                    </学号>
                    <姓名>
                        <xsl:value-of select="jw:name" />
                    </姓名>
                    <性别>
                        <xsl:value-of select="jw:sex" />
                    </性别>
                    <院系>
                        <xsl:value-of select="jw:major" />
                    </院系>
                </student>
            </xsl:for-each>
        </Students>
    </xsl:template>
</xsl:stylesheet>