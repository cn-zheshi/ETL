<?xml version= "1.0" encoding= "gb2312"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" encoding="gb2312" />
    <xsl:template match="/">
        <xsl:apply-templates />
    </xsl:template>
    <xsl:template match="Classes">
        <Classes>
            <xsl:for-each select="class">
                <class>
                    <编号>
                        <xsl:value-of select="id" />
                    </编号>
                    <名称>
                        <xsl:value-of select="name" />
                    </名称>
                    <学分>
                        <xsl:value-of select="score" />
                    </学分>
                    <老师>
                        <xsl:value-of select="teacher" />
                    </老师>
                    <地点>
                        <xsl:value-of select="location" />
                    </地点>
                    <共享>
                        <xsl:value-of select="share" />
                    </共享>
                </class>
            </xsl:for-each>
        </Classes>
    </xsl:template>
</xsl:stylesheet>