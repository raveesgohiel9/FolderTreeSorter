 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsorter;

import java.io.File;




/**
 *
 * @author ravi_gohil999
 */
public class FSorter {

    static FSorter fsorter;
    String path1 = "D:\\";
    String path2 = "G:\\Data\\";
    String basePath = path2;
    
    folderStruct fstruct;
    folderStruct fstruct1;
    DirectoryStructure mainDirectory;
    DirectoryStructure duplicateDirectory;
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
       
        fsorter = new FSorter();
        
        /*
        fsorter.mainDirectory = new DirectoryStructure();
        fsorter.mainDirectory.sort("Test");
        fsorter.mainDirectory.displayDirectories();
        fsorter.mainDirectory.displayFiles();
        
        fsorter.duplicateDirectory = new DirectoryStructure();
        fsorter.duplicateDirectory = new DirectoryStructure();
        fsorter.duplicateDirectory.sort("Data");
        fsorter.duplicateDirectory.displayDirectories();
        fsorter.duplicateDirectory.displayFiles();
        
        //fsorter.mainDirectory.mergeFolders(fsorter.mainDirectory, fsorter.duplicateDirectory);
        */
        
        fsorter.fstruct = new folderStruct();
       //fsorter.fstruct.sort(fsorter.basePath+"Test");
        fsorter.fstruct.sort(fsorter.basePath+"Albums");
        
        System.out.println("<<<<----->>>>");
        
        fsorter.fstruct1 = new folderStruct();
        
        //fsorter.fstruct1.sort1(fsorter.basePath+"Data",fsorter.fstruct,fsorter.basePath+"Test\\Other\\");
        //fsorter.fstruct.compare(fsorter.fstruct, fsorter.fstruct1, fsorter.basePath+"Albums_other\\");
        
        
        
        fsorter.fstruct1.sort1(fsorter.basePath+"Albums_copy",fsorter.fstruct,fsorter.basePath+"Albums_other\\");
        fsorter.fstruct1.reset();
        
        System.out.println("<<<<----->>>>");
        
        fsorter.fstruct1.sort1(fsorter.basePath+"Dropbox",fsorter.fstruct,fsorter.basePath+"Dropbox_other\\");
        //fsorter.fstruct.compare(fsorter.fstruct, fsorter.fstruct1, fsorter.basePath+"Dropbox_other\\");
        fsorter.fstruct1.reset();
        
        System.out.println("<<<<----->>>>");
        
        fsorter.fstruct1.sort1(fsorter.basePath+"Dropbox_copy",fsorter.fstruct,fsorter.basePath+"Dropbox_copy_other\\");
        //fsorter.fstruct.compare(fsorter.fstruct, fsorter.fstruct1, fsorter.basePath+"Dropbox_copy_other\\");
        fsorter.fstruct1.reset();
        
        System.out.println("<<<<----->>>>");
        
        fsorter.fstruct1.sort1(fsorter.basePath+"photo2TB",fsorter.fstruct,fsorter.basePath+"photo2TB_other\\");
        //fsorter.fstruct.compare(fsorter.fstruct, fsorter.fstruct1, fsorter.basePath+"photo2TB_other\\");
        fsorter.fstruct1.reset();
        
        
                
        
        
    }
    
    
    
    
    public static  FSorter getInstance()
    {
        return fsorter;
        
    }
    
}
