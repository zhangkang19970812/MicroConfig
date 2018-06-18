package com.nju.tutorialtool.util.git;

import org.eclipse.jgit.api.*;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class GitUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(GitUtil.class);

    public static boolean clone(String remoteUrl, String localDir) {
        boolean res = false;
        File file = new File(localDir);
        if (file.exists()) {
            deleteFolder(file);
        }
        try {
            Git git = Git.cloneRepository()
                    .setURI(remoteUrl)
                    .setDirectory(file)
                    .call();
            LOGGER.info("Cloning from " + remoteUrl + " to " + git.getRepository());
            res = true;
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
        return res;
    }

    public static void commit(String localRepoGitConfig){
        File file = new File(localRepoGitConfig);
        if (!file.exists()) {
            return;
        }
        try {
            Git git = Git.open(file);
            //创建用户文件的过程
//            File myfile = new File(filePath);
//            myfile.createNewFile();
//            git.add().addFilepattern("pets").call();
            //提交
            git.commit().setMessage("").call();
            git.push().setCredentialsProvider( new UsernamePasswordCredentialsProvider( "zhangkang19970812", "zhang19970812")).call();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void commitAndPush(String localRepoGitConfig, String username, String password) throws Exception {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = builder.setGitDir(new File(localRepoGitConfig))
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build();
        Git git = new Git(repository);
        AddCommand add = git.add();
        add.addFilepattern(".").call();//git add .
        CommitCommand commit = git.commit();
        /**-Dusername=%teamcity.build.username%**/
        commit.setCommitter("MSConfig", "");
        commit.setAuthor("MSConfig","");
        commit.setAll(true);
        //commit.setCommitter(new PersonIdent(repository));
        RevCommit revCommit = commit.setMessage("MSConfig").call();//git commit -m "use jgit"
        String commitId = revCommit.getId().name();
        System.out.println(commitId);
        PushCommand push = git.push().setCredentialsProvider( new UsernamePasswordCredentialsProvider( username, password));
        push.call();//git push
    }

    public static void deleteFolder(File file){
        if(file.isFile() || file.list().length==0){
            file.delete();
        }else{
            File[] files = file.listFiles();
            for(int i=0;i<files.length;i++){
                deleteFolder(files[i]);
                files[i].delete();
            }
        }
    }

    public static void addRemoteRepo() throws Exception {
        Git git = Git.init().setDirectory(new File("C:/Users/zk/Desktop/account_mysql")).call();
        RemoteAddCommand remoteAddCommand = git.remoteAdd();
        remoteAddCommand.setName("origin");
        remoteAddCommand.setUri(new URIish("https://github.com/zhangkang19970812/account_mysql"));
        remoteAddCommand.call();
    }

//    public static void main(String[] args) throws Exception {
//        GitUtil.clone("https://github.com/zhangkang19970812/test", "C:/Users/zk/Desktop/test");
//        GitUtil.commitAndPush("C:/Users/zk/Desktop/test/.git","zhangkang19970812", "zhang19970812");
//        GitUtil.addRemoteRepo();
//    }
}
