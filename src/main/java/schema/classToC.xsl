<?xml version= "1.0" encoding= "gb2312"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:jw="http://jw.nju.edu.cn/schema">
    <xsl:output method="xml" encoding="gb2312" />
    <xsl:template match="/">
        <xsl:apply-templates />
    </xsl:template>
    <xsl:template match="jw:Classes">
        <Classes>
            <xsl:for-each select="jw:class">
                <class>
                    <Cno>
                        <xsl:value-of select="jw:id" />
                    </Cno>
                    <Cnm>
                        <xsl:value-of select="jw:name" />
                    </Cnm>
                    <Cpt>
                        <xsl:value-of select="jw:score" />
                    </Cpt>
                    <Tec>
                        <xsl:value-of select="jw:teacher" />
                    </Tec>
                    <Pla>
                        <xsl:value-of select="jw:location" />
                    </Pla>
                </class>
            </xsl:for-each>
        </Classes>
    </xsl:template>
</xsl:stylesheet>