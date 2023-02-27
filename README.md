# Version_Control_System1
https://hyperskill.org/projects/177/stages/912/implement
Project: Version Control System

1/
Description
In this stage, you should implement two commands. Commit will allow a user to save file changes; log will allow viewing the commit history.

Git may seem quite complicated. If you want to learn more, watch the video explanation by GitLab.

The purpose of this project is to work with files. Store different file versions in the index according to the commits and make sure that each commit has a unique ID. ID should allow you to find a specific file version that matches this commit. You need to think about the concept of a commit ID.

You can store commits inside vsc/commits. Each commit will be saved to a separate directory. These directories will include a commit's ID and additional information that you can store in vsc/log.txt.

You will also need to find out whether a file has been changed since the last commit. For that, you need to calculate the hash of the current file and the hash of the last commit. If these values are different, then the file has been changed. Use Java Cryptography Architecture (JCA). JCA includes solutions that are based on various cryptographic algorithms such as SHA1, SHA256, and others. Hash functions are optional, you can use a different solution.

Objectives
Implement the following commands:

commit must be passed to the program along with a message (see examples). Save all changes. Each commit must be assigned a unique id. if there were no changes since the last commit, do not create a new commit. You don't need to optimize the storage of changes, just copy all the staged files to the commit folder every time.
log should show all the commits in reverse order.

2\
Description
In the last stage, implement the checkout command. It allows a user to switch between commits and restore the contents of the files according to the current commit.

Get the files you need from the commit directory by the commit id and rewrite the current files.

.
├── vcs
│   ├── commits
│   │   ├── 2853da19f31cfc086cd5c40915253cb28d5eb01c
│   │   │   ├── file1.txt
│   │   │   └── file2.txt
│   │   └── 0b4f05fcd3e1dcc47f58fed4bb189196f99da89a
│   │       ├── file1.txt
│   │       └── file2.txt
│   ├── config.txt
│   ├── index.txt
│   └── log.txt
├── file1.txt
├── file2.txt
└── untracked_file.txt
Objectives
The checkout command must be passed to the program together with the commit ID to indicate which commit should be used. If a commit with the given ID exists, the contents of the tracked file should be restored in accordance with this commit.

