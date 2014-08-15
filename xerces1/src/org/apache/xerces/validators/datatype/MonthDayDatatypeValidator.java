
/*
 * The Apache Software License, Version 1.1
 *
 *
 * Copyright (c) 1999, 2000 The Apache Software Foundation.  All rights 
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:  
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Xerces" and "Apache Software Foundation" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written 
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation and was
 * originally based on software copyright (c) 2001, International
 * Business Machines, Inc., http://www.apache.org.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */

package org.apache.xerces.validators.datatype;

import java.util.Hashtable;

/**
 * Validator for <gMonthDay> datatype (W3C Schema Datatypes)
 * 
 * @author Elena Litani
 * @version $Id: MonthDayDatatypeValidator.java 317299 2001-06-21 20:56:13Z elena $
 */

public class MonthDayDatatypeValidator extends DateTimeValidator {

    //size without time zone: --MM-DD    
    private final static int MONTHDAY_SIZE = 7;

    public  MonthDayDatatypeValidator() throws InvalidDatatypeFacetException{
        super();
    }

    public  MonthDayDatatypeValidator ( DatatypeValidator base, Hashtable facets, 
                                        boolean derivedByList ) throws InvalidDatatypeFacetException {
        super(base, facets, derivedByList);
    }

    /** 
     * Parses, validates and computes normalized version of gMonthDay object
     * 
     * @param str    The lexical representation of gMonthDay object --MM-DD
     *               with possible time zone Z or (-),(+)hh:mm
     * @param date   uninitialized date object
     * @return normalized date representation
     * @exception Exception Invalid lexical representation
     */
    protected int[] parse(String str, int[] date) throws SchemaDateTimeException{

        resetBuffer(str);

        //create structure to hold an object
        if ( date== null ) {
            date=new int[TOTAL_SIZE];
        }
        resetDateObj(date);

        //initialize 
        date[CY]=YEAR;

        if (fBuffer.charAt(0)!='-' || fBuffer.charAt(1)!='-') {
            throw new SchemaDateTimeException("Invalid format for gMonthDay: "+str);
        }
        date[M]=parseInt(fStart+2,fStart+4);
        fStart+=4;
        
        if (fBuffer.charAt(fStart++)!='-') {
            throw new SchemaDateTimeException("Invalid format for gMonthDay: " + str);
        }
        
        date[D]=parseInt(fStart, fStart+2);

        if ( MONTHDAY_SIZE<fEnd ) {
            int sign = findUTCSign(MONTHDAY_SIZE, fEnd);
            if ( sign<0 ) {
                throw new SchemaDateTimeException ("Error in month parsing:" +str);
            }
            else {
                getTimeZone(date, sign);
            }
        }
        //validate and normalize

        //REVISIT: do we need SchemaDateTimeException?
        validateDateTime(date);
        
        if ( date[utc]!=0 && date[utc]!='Z' ) {
            normalize(date);
        }
        return date;
    }


    /**
     * Converts gMonthDay object representation to String
     * 
     * @param date   gmonthDay object
     * @return lexical representation of month: --MM-DD with an optional time zone sign
     */
    protected String dateToString(int[] date) {
        message.setLength(0);
        message.append('-');
        message.append('-');
        message.append(date[M]);
        message.append('-');
        message.append(date[D]);
        message.append((char)date[utc]);
        return message.toString();
    }

}

