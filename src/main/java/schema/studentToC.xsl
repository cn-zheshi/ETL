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
                    <Sno>
                        <xsl:value-of select="jw:id" />
                    </Sno>
                    <Snm>
                        <xsl:value-of select="jw:name" />
                    </Snm>
                    <Sex>
                        <xsl:value-of select="jw:sex" />
                    </Sex>
                    <Sde>
                        <xsl:value-of select="jw:major" />
                    </Sde>
                </student>
            </xsl:for-each>
        </Students>
    </xsl:template>
</xsl:stylesheet>