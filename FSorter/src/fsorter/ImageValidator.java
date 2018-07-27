/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsorter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Ravi Gohi
 */
public class ImageValidator{

   private Pattern pattern;
   private Matcher matcher;

   private static final String IMAGE_PATTERN =
                "((.*/)*.+\\.(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP)$)";

   public ImageValidator(){
	  pattern = Pattern.compile(IMAGE_PATTERN);
   }

   /**
   * Validate image with regular expression
   * @param image image for validation
   * @return true valid image, false invalid image
   */
   public boolean validate(final String image){
          
       //System.out.println("Image:"+image);
	  matcher = pattern.matcher(image);
          return matcher.matches();

   }
}
