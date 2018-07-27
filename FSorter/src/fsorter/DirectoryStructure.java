/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fsorter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 *
 * @author Ravi Gohi
 */

public class DirectoryStructure {

    HashMap<Integer,Node> nodeslist;
    HashMap<Integer,String> visitedlist;
    int nodecount = 0;
    int filecount = 0;
    String basepath = "D:\\";
    Node root;
    Stack<Node> visit;
    //Stores file name and the folder id
    HashMap<String,Integer> fileslist;
    ArrayList<String> fileNames;
    public DirectoryStructure() {
        nodeslist = new HashMap<>();
        fileslist = new HashMap<>();
        visit = new Stack<>();
        fileNames = new ArrayList<>();
    }
    
    public void sort(String rootName)
    {
        if(nodecount == 0)
        {
            root = new Node();
            root.name = rootName;
            root.key = nodecount;
            root.path = basepath +File.separator+ rootName;
            nodeslist.put(nodecount, root);
           
            nodecount++;
            
            File[] listSubDirs = listSubdirs(root.path);
            for(int i = 0; i < listSubDirs.length;i++)
            {
                if(listSubDirs[i].isDirectory())
                {
                    
                   // Node tp = new Node();
                    Node tp = addSubChild(listSubDirs[i].getName(),nodecount,root.path + File.separator + listSubDirs[i].getName(),root.key);
                    root.childnodes.add(nodecount);
                    nodeslist.put(nodecount, tp);
                    visit.push(tp);
                    nodecount++;
                    
                }
                else
                {
                    fileslist.put(listSubDirs[i].getName(), root.key);
                    fileNames.add(listSubDirs[i].getName());
                    root.filenodes.add(filecount);
                    filecount++;
                }
            }
        }
        
        if(root.childnodes.size() > 0)
        {
            print("Break here");
            while(!visit.empty())
            {
                Node curr = visit.peek();
                visit.pop();
                 File[] listSubDirs = listSubdirs(curr.path);
                for(int i = 0; i < listSubDirs.length;i++)
                {
                    if(listSubDirs[i].isDirectory())
                    {
                        Node tp = addSubChild(listSubDirs[i].getName(),nodecount,curr.path + File.separator + listSubDirs[i].getName(),curr.key);
                        curr.childnodes.add(nodecount);
                        nodeslist.put(nodecount, tp);
                        visit.push(tp);
                        nodecount++;
                    }
                    else
                    {
                        
                        fileslist.put(listSubDirs[i].getName(), curr.key);
                        fileNames.add(listSubDirs[i].getName());
                        curr.filenodes.add(filecount);
                        filecount++;
                    }
                }
                 
            }
            
            print("Break here");
        }
        
    }
    
    public void displayDirectories()
    {
        Node curr = root;
        int i = 0;
        while(i < nodecount)
        {
            print("Node:"+curr.name+"->Path:"+curr.path);
            i++;
            curr = nodeslist.get(i);
        }
    }
    public void displayFiles()
    {
        int i = 0;
        while(i < fileslist.size())
        {
            String fileName = fileNames.get(i);
            int folderID = fileslist.get(fileName);
            Node file_folder = nodeslist.get(folderID);
            File nFile = new File(file_folder.path+File.separator+fileName);
            //BasicFileAttributes attr = Files.readAttributes(nFile, BasicFileAttributes.class, );
            print("File Name:"+nFile.getName()+" ->Path->"+nFile.getPath());
            //print("File Name:"+nFile.+" ->Path->"+file_folder.path+File.separator+fileName);
            
            print("-<>-<>-<>-<>-<>-<>-<>-<>-<>-<>-<>-<>-<>");
            i++;
        }
    }
    
       public void mergeFolders(DirectoryStructure set1,DirectoryStructure set2)
    {
        Stack<Node> S1 = new Stack();
        Stack<Node> S2 = new Stack();
        
        S1.push(set1.root);
        S2.push(set2.root);
        
        //Name of the folder and folder id
        HashMap<String,Node> childList = new HashMap<>();
        
        
            
            while(!S1.empty())
            {
                Node curr1 = S1.peek();
                Node curr2 = S2.peek();
                if(curr1.name.equals(curr2.name))
                {
                
                    S1.pop();
                    S2.pop();

                    System.out.println("@@@Curr1 childnodes size:"+curr1.childnodes.size());
                    
                     for(int i = 0;i < curr1.childnodes.size();i++)
                    {
                        int folder_id = curr1.childnodes.get(i);
                        Node tp = nodeslist.get(folder_id);
                        String folder_name = tp.name;
                        System.out.println("Curr1->Folder id:"+curr1.childnodes.get(i)+"->Folder name:"+folder_name);
                        childList.put(folder_name,tp);

                        S1.push(tp);
                    
                    }
                    
                    System.out.println("@@@Curr2 childnodes size:"+curr2.childnodes.size());
                    for(int j = 0;j < curr2.childnodes.size();j++)
                    {

                        int folder_id = curr2.childnodes.get(j);
                        Node tp = set2.nodeslist.get(folder_id);
                        String folder_name = tp.name;
                        System.out.println("Curr2->Folder id:"+curr2.childnodes.get(j)+"->Folder name:"+folder_name);
                        if(!childList.containsKey(folder_name))
                        {
                            if( new File(curr1.path+File.separator+folder_name).mkdir())
                            {
                                Node tp1 = addSubChild(folder_name, nodecount,curr1.path+File.separator+folder_name, curr1.key);

                                curr1.childnodes.add(nodecount);
                                nodeslist.put(nodecount, tp1);
                                nodecount++;
                                childList.put(folder_name,tp1);
                                S1.push(tp1);
                            }
                            else
                            {
                                System.out.println("********Error creating folder->"+folder_name+" in Path"+curr1.path);
                            }


                        }
                        S2.push(tp);
                    }

                    childList.clear();
                }
            }
        
           
        
    }
    public void copy(DirectoryStructure set1,DirectoryStructure set2)
    {
        Stack<Node> S1 = new Stack();
        Stack<Node> S2 = new Stack();
        
        S1.push(set1.root);
        S2.push(set2.root);
        
        
        
    }
    public void checkDuplicates(File from, File to)
    {
        try
        {
            Path from_path = Paths.get(from.getAbsolutePath());
            BasicFileAttributes attr_from = Files.readAttributes(from_path, BasicFileAttributes.class);
        }
        catch(IOException e)
        {
            e.printStackTrace();
            
        }
        
    }
       
     public void print(String s)
    {
        System.out.println(s);
    }
    protected Node addSubChild(String nName,int nKey,String nPath,int nParentKey)
    {
        Node tp = new Node();
        tp.name = nName;
        tp.key = nKey;
        tp.path = nPath;
        tp.parentkey = nParentKey;
        return tp;
    }
     public File[] listSubdirs(String path)
    {
         
        File baseDir = new File(path);
        File[] listSubdirs = baseDir.listFiles();
        
        return  listSubdirs;
    }
    class Node
    {
        int  key;
        String name;
        int parentkey;
        ArrayList<Integer> childnodes;
        ArrayList<Integer> filenodes;
        String path;
        Node()
        {
            childnodes = new ArrayList<>();
            filenodes = new ArrayList<>();
            name = "";
        }
        
    }
    
    class FileNode
    {
        String name;
        
    }
    
}


