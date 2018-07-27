/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsorter;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;


/**
 *
 * @author ravi_gohil999
 */
public class folderStruct {
   
    double filecount = 0;
    //Store the file and the path info
    HashMap<FileNode,String> files;
    ArrayList<FileNode> fileNodes;
    ArrayList<Integer> filesHashCode;
    
    ImageValidator iv;
    
    public folderStruct() {
        files = new HashMap<>();
        fileNodes = new ArrayList<>();
        filesHashCode = new ArrayList<>();
        iv = new ImageValidator();
    }
    
    
   
    public void sort(String root)
    {
        Stack<String> S = new Stack<>();
        S.push(root);
        
        while(!S.empty())
        {
            String curr = S.peek();
            S.pop();
            File[] listfiles = listSubdirs(curr);
            for (File listfile : listfiles) {
                
                if (!listfile.isDirectory()) {
                    
                    try
                    {
                        //System.out.println("File:"+listfile.getPath());
                        Image img = ImageIO.read(listfile);
                        
                        if(img!=null)
                        {
                            //System.out.println("Adding");
                            FileNode fn = getFileNode(listfile.getPath());
                            files.put(fn, listfile.getPath());
                            fileNodes.add(fn);
                            filesHashCode.add(createhashCode(fn));
                            //System.out.println("File:"+fn.name);
                            filecount++;
                           
                        }
                        else
                        {
                            
                            //System.out.println("------------->>>>>>>>>>>>>>>>>>>Ignoring file:"+listfile.getName());
                        }
                         img = null;
                    }
                    
                    catch(IOException e)
                    {
                        System.out.println("File:"+listfile.getName()+"->FileCount:"+filecount);
                        e.printStackTrace();
                    }
                    
                    catch(Exception e)
                    {
                        
                        System.out.println("Problem here");
                        e.printStackTrace();
                    }
                        
                    
                } else {
                    S.push(listfile.getPath());
                }
            }
            listfiles = null;
            System.out.println("Total files:"+filecount);
            
        }
    }
    
    public void sort1(String root,folderStruct S1,String path)
    {
        int fileCopyCounter = 0;
        Stack<String> S = new Stack<>();
        S.push(root);
        
        while(!S.empty())
        {
            String curr = S.peek();
            S.pop();
            File[] listfiles = listSubdirs(curr);
            for (File listfile : listfiles) {
                
                if (!listfile.isDirectory()) {
                    
                    try
                    {
                        //System.out.println("File:"+listfile.getPath());
                        Image img = ImageIO.read(listfile);
                        
                        if(img!= null)
                        {
                            FileNode fn = getFileNode(listfile.getPath());
                            if(S1.filesHashCode.contains(createhashCode(fn)))
                            {
                                System.out.println("File exists");
                            }
                            else
                            {
                                //Move file to the new folder
                                File source = listfile;
                                File dest = new File(path+File.separator+listfile.getName());

                                try{
                                    System.out.println("Copying file");
                                    //System.out.println("Source:"+source);
                                    //System.out.println("Dest:"+dest);
                                    FileUtils.copyFile(source, dest);

                                    //Add the file in the list so another duplicate file is not copied again
                                    S1.files.put(fn,dest.getPath());
                                    S1.fileNodes.add(fn);
                                    filesHashCode.add(createhashCode(fn));
                                    S1.filecount++;
                                    fileCopyCounter++;
                                } 
                                
                                catch (IOException e) {
                                    e.printStackTrace();
                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }
                                
                           
                        }
                        else
                        {
                            
                            //System.out.println("------------->>>>>>>>>>>>>>>>>>>Ignoring file:"+listfile.getName());
                        }
                         //img = null;
                    }
                    /*
                    catch(IOException e)
                    {
                        System.out.println("File:"+listfile.getName()+"->FileCount:"+filecount);
                        e.printStackTrace();
                    }
                    */
                    catch(Exception e)
                    {
                        System.out.println("Problem here");
                        e.printStackTrace();
                    }
                        
                    
                } else {
                    S.push(listfile.getPath());
                }
            }
            listfiles = null;
            System.out.println("Total files copied:"+fileCopyCounter);
            
        }
    }
    
    public void compare(folderStruct S1,folderStruct S2,String path)
    {
        int fileCopyCounter = 0;
        
        for(FileNode fn:S2.fileNodes)
        {
            if(S1.filesHashCode.contains(createhashCode(fn)))
            {
                
                System.out.println("S1->File:"+S1.files.get(fn)+" S2->File:"+S2.files.get(fn));
            }
            else
            {
                //Move file to the new folder
                File source = new File(S2.files.get(fn));
                File dest = new File(path+File.separator+fn.name);
                 
                try{
                    System.out.println("Copying file");
                    System.out.println("Source:"+source);
                    System.out.println("Dest:"+dest);
                    FileUtils.copyFile(source, dest);
                    
                    
                    //Add the file in the list so another duplicate file is not copied again
                    S1.files.put(fn,dest.getPath());
                    S1.fileNodes.add(fn);
                    S1.filecount++;
                    fileCopyCounter++;
                } 
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Total files copied:"+fileCopyCounter);
    }
   
    public void compareTest(folderStruct S1,folderStruct S2,String path)
    {
        int fileCopyCounter = 0;
        for(FileNode fn:S2.fileNodes)
        {
            if(S1.filesHashCode.contains(createhashCode(fn)))
            {
                System.out.println("Contains:");
            }
            else
            {
                //Move file to the new folder
                File source = new File(S2.files.get(fn));
                File dest = new File(path+fn.name);
                 
                
                    System.out.println("Copying file");
                    System.out.println("Source:"+source);
                    System.out.println("Dest:"+dest);
                    //FileUtils.copyFile(source, dest);
                    
                    //Add the file in the list so another duplicate file is not copied again
                    S1.files.put(fn,dest.getPath());
                    S1.fileNodes.add(fn);
                    S1.filecount++;
                    fileCopyCounter++;
               
            }
        }
        
        System.out.println("Total files copied:"+fileCopyCounter);
    }
    
    private int createhashCode(FileNode f)
    {
        int h = f.name.hashCode() ^ Integer.toString(f.width).hashCode() ^ Integer.toString(f.height).hashCode() ^ Long.toString(f.filesize).hashCode();
        return h;
    }
   
    public FileNode getFileNode(String path)
    {
        FileNode fn = new FileNode();
        try
        {
            Path from_path = Paths.get(path);
            BasicFileAttributes attr_from = Files.readAttributes(from_path, BasicFileAttributes.class);
            File nFile = new File(path);
            BufferedImage nImage = ImageIO.read(nFile);
            
//            System.out.println("Name->"+nFile.getName());
//            System.out.println("Size->"+attr_from.size());
//            System.out.println("Date Created->"+attr_from.creationTime());
//            
//            System.out.println("Width->"+nImage.getWidth());
//            System.out.println("Height->"+nImage.getHeight());
            
            fn = new FileNode(nFile.getName(), attr_from.creationTime(), nImage.getWidth(), nImage.getHeight(), attr_from.size());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return fn;
    }
    
     public File[] listSubdirs(String path)
    {
         
        File baseDir = new File(path);
        File[] listSubdirs = baseDir.listFiles();
        
        return  listSubdirs;
    }
     public void reset()
     {
         
         files.clear();
        fileNodes.clear();
        filesHashCode.clear();
     }
    class FileNode
    {
        String name;
        FileTime datecreated;
        int width;
        int height;
        long filesize;
       int hashcode;
        FileNode()
        {
            
        }
        
        FileNode(String name, FileTime datecreated, int width,int height,long filesize)
        {
            this.name = name;
            //this.datecreated = datecreated;
            
            this.width = width;
            this.height = height;
            this.filesize = filesize;
           
        }
        
        @Override
        public int hashCode() {
            
            hashcode = (name.hashCode() ^ Integer.toString(width).hashCode() ^ Integer.toString(height).hashCode() ^ Long.toString(filesize).hashCode());
            //System.out.println("HashCode:"+hashcode);
            return hashcode; //To change body of generated methods, choose Tools | Templates.
        }

        
        
        
        
    }
}
