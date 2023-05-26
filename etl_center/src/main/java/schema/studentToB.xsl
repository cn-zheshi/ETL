<?xml version= "1.0" encoding= "gb2312"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" encoding="gb2312" />
    <xsl:template match="/">
        <xsl:apply-templates />
    </xsl:template>
    <xsl:template match="Students">
        <Students>
            <xsl:for-each select="student">
                <student>
                    <ѧ��>
                        <xsl:value-of select="id" />
                    </ѧ��>
                    <����>
                        <xsl:value-of select="name" />
                    </����>
                    <�Ա�>
                        <xsl:value-of select="sex" />
                    </�Ա�>
                    <רҵ>
                        <xsl:value-of select="major" />
                    </רҵ>
                </student>
            </xsl:for-each>
        </Students>
    </xsl:template>
</xsl:stylesheet>