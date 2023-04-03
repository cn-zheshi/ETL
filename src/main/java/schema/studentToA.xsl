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
                    <ѧ��>
                        <xsl:value-of select="jw:id" />
                    </ѧ��>
                    <����>
                        <xsl:value-of select="jw:name" />
                    </����>
                    <�Ա�>
                        <xsl:value-of select="jw:sex" />
                    </�Ա�>
                    <Ժϵ>
                        <xsl:value-of select="jw:major" />
                    </Ժϵ>
                </student>
            </xsl:for-each>
        </Students>
    </xsl:template>
</xsl:stylesheet>