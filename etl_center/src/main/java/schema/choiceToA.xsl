<?xml version= "1.0" encoding= "gb2312"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" encoding="gb2312" />
    <xsl:template match="/">
        <xsl:apply-templates />
    </xsl:template>
    <xsl:template match="Choices">
        <Choices>
            <xsl:for-each select="choice">
                <choice>
                    <ѧ��>
                        <xsl:value-of select="sid" />
                    </ѧ��>
                    <�γ̱��>
                        <xsl:value-of select="cid" />
                    </�γ̱��>
                    <�ɼ�>
                        <xsl:value-of select="score" />
                    </�ɼ�>
                </choice>
            </xsl:for-each>
        </Choices>
    </xsl:template>
</xsl:stylesheet>