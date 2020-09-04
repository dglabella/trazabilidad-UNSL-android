package com.unsl.trazabilidadunsl.utils;

public class StringUtils
{
    public static int countMatches(String s, char c)
    {
        int ret = 0;

        for(int i = 0; i < s.length(); i++)
        {
            if(c == s.charAt(i))
            {
                System.out.println(c +" -- "+ s.charAt(i));
                ret++;
            }
        }

        return ret;
    }
}
