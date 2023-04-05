<?xml version= "1.0" encoding= "gb2312"?>
<xsl:stylesheet version= "1.0" xmlns:xsl= "http://www.w3.org/1999/XSL/Transform">
    <xsl:output method= "xml" encoding= "gb2312"/>
    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>
    <xsl:template match="Classes">
        <Classes>
            <xsl:for-each select="class">
                <class>
                    <id>
                        <xsl:value-of select="�γ̱��"/>
                        <xsl:value-of select="���"/>
                        <xsl:value-of select="Cno"/>
                    </id>
                    <name>
                        <xsl:value-of select="����"/>
                        <xsl:value-of select="�γ�����"/>
                        <xsl:value-of select="Cnm"/>
                    </name>
                    <xsl:choose>
                        <xsl:when test="./Cpt=''">
                            <score>
                                <xsl:value-of select="ѧ��"/>
                            </score>
                        </xsl:when>
                        <xsl:when test="./Cpt!=''">
                            <score>
                                <xsl:value-of select="Cpt"/>
                            </score>
                        </xsl:when>
                    </xsl:choose>

                    <teacher>
                        <xsl:value-of select="�ڿ���ʦ"/>
                        <xsl:value-of select="��ʦ"/>
                        <xsl:value-of select="Tec"/>
                    </teacher>

                    <location>
                        <xsl:value-of select="�ڿεص�"/>
                        <xsl:value-of select="�ص�"/>
                        <xsl:value-of select="Pla"/>
                    </location>
                </class>
            </xsl:for-each>
        </Classes>
    </xsl:template>
</xsl:stylesheet>