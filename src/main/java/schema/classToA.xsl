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
                    <课程编号>
                        <xsl:value-of select="jw:id" />
                    </课程编号>
                    <课程名称>
                        <xsl:value-of select="jw:name" />
                    </课程名称>
                    <学分>
                        <xsl:value-of select="jw:score" />
                    </学分>
                    <授课老师>
                        <xsl:value-of select="jw:teacher" />
                    </授课老师>
                    <授课地点>
                        <xsl:value-of select="jw:location" />
                    </授课地点>
                </class>
            </xsl:for-each>
        </Classes>
    </xsl:template>
</xsl:stylesheet>