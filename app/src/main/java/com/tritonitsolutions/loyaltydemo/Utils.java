package com.tritonitsolutions.loyaltydemo;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by TritonDev on 9/5/2015.
 */
public class Utils {
    public static void CopyStream(InputStream is,OutputStream os){
        final int buffer_size=1024;
        try {
            byte[] b=new byte[buffer_size];
            for(;;){
                int count=is.read(b,0,buffer_size);
                if(count==-1)
                    break;
                os.write(b,0,count);
            }


        }catch (Exception ex){
        }
    }
}
