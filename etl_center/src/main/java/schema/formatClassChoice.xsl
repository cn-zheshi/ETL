<?xml version= "1.0" encoding= "gb2312"?>
<xsl:stylesheet version= "1.0" xmlns:xsl= "http://www.w3.org/1999/XSL/Transform">
    <xsl:output method= "xml" encoding= "gb2312"/>
    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>
    <xsl:template match="Choices">
        <Choices>
            <xsl:for-each select="choice">
                <choice>
                    <sid>
                        <xsl:value-of select="ѧ�����"/>
                        <xsl:value-of select="ѧ��"/>
                        <xsl:value-of select="Sno"/>
                    </sid>
                    <cid>
                        <xsl:value-of select="�γ̱��"/>
                        <xsl:value-of select="Cno"/>
                    </cid>
                    <score>
                        <xsl:value-of select="�ɼ�"/>
                        <xsl:value-of select="�÷�"/>
                        <xsl:value-of select="Grd"/>
                    </score>
                </choice>
            </xsl:for-each>
        </Choices>
    </xsl:template>
</xsl:stylesheet>