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
                    <���>
                        <xsl:value-of select="id" />
                    </���>
                    <����>
                        <xsl:value-of select="name" />
                    </����>
                    <ѧ��>
                        <xsl:value-of select="score" />
                    </ѧ��>
                    <��ʦ>
                        <xsl:value-of select="teacher" />
                    </��ʦ>
                    <�ص�>
                        <xsl:value-of select="location" />
                    </�ص�>
                </class>
            </xsl:for-each>
        </Classes>
    </xsl:template>
</xsl:stylesheet>