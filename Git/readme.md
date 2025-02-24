# Git: A Visual Guide
This guide provides a step-by-step introduction to Git fundamentals, with visual examples to help you understand each concept.
## Basics
### Initial Setup
Before you start using Git, you need to configure your identity. Git uses this information to associate commits with an author.

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Git/Basics/Images/1.png)

These commands set your name and email globally, meaning they'll be used for all Git repositories on your system unless overridden locally.

### Creating a New Repository
To start version controlling a project, you first need to initialize a Git repository:

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Git/Basics/Images/2.png)

When you run this command, Git creates a hidden .git directory that stores all the version control information. By default, Git uses 'master' as the initial branch name, though 'main' is becoming more common.

### Working with Files
Adding New Files
When you create new files in your repository, Git won't automatically track them. Let's look at the workflow:

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Git/Basics/Images/3.png)

Check status:
Git will show these as "Untracked files" in red, indicating they're not yet being version controlled.

Stage files:

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Git/Basics/Images/4.png)

This command tells Git to start tracking these files and include them in the next commit.

### Making Changes
After making changes to tracked files and tracking them using the add command, you are now ready to commit:

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Git/Basics/Images/5.png)

### Viewing History
Git maintains a complete history of all changes. You can view it using:

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Git/Basics/Images/8.png)

The log shows:
```
Commit hash (a unique identifier)
Author information
Date and time
Commit message
```
For a more concise view:
`git log --oneline`
This will print the information into a single line, making the information easier to track.

## Branching
Branches are one of Git's most powerful features. Think of them as parallel universes for your code, where you can work on different features or fixes without affecting the main codebase. Here's how to work with them:
### Viewing Branches
To see all your branches:

`git branch`

The current branch is marked with an asterisk (*) and typically shown in green.

### Creating Branches

You can create a new branch using:
`git branch <branch-name>`

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Git/Branching/Images/1.png)

Or create and switch to it in one command:
`git switch -c <branch-name>`


### Switching Between Branches
To move between branches:
`git switch <branch-name>`

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Git/Branching/Images/2.png)

When you switch branches, Git updates your working directory to match the branch's state. Any files you see will reflect the content of that branch.

### Deleting Branches
Once you're done with a branch, you can delete it:
git branch -D <branch-name>

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Git/Branching/Images/3.png)

### Renaming Branches
To rename a branch:
`git branch -m <new-name>`

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Git/Branching/Images/4.png)

## Understanding Changes with Git Diff
Git provides powerful tools for comparing different versions of your files. The git diff command helps you understand exactly what has changed between any two points in your repository's history.
### Basic Diff Usage
The simplest form of diff shows changes in your working directory:
`git diff`
This command displays:
```
Lines that begin with - (shown in red) indicate removed content
Lines that begin with + (shown in green) indicate added content
Lines without either symbol remain unchanged
The @@ markers show the location of the change in the file
```

### Comparing Specific Versions
You can compare any two points in history:
`git diff <reference1> <reference2>`
For example, to compare changes between two branches or commits:

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Git/Diff/1.png)

You can also do this for specific files rather than the entire repo:

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Git/Diff/2.png)

### Understanding Diff States
Git diff shows different types of changes depending on where files are in the Git workflow:

Working Directory vs Staging Area:
`git diff`
Shows changes that aren't yet staged

Staging Area vs Last Commit:
`git diff --staged`
Shows changes that are staged but not yet committed

The differences can be seen below:

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Git/Diff/4.png)

Between Commits:
`git diff HEAD HEAD~1`
Shows changes between the current commit and the previous one

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Git/Diff/3.png)

Remember that diff is your window into what's changing in your codebase. Getting comfortable with reading diffs will help you catch mistakes before they're committed and understand the evolution of your project over time.

## Time Travel with Git Checkout
Git checkout is like a time machine for your code, allowing you to navigate through your project's history and inspect or restore previous versions of your files. Understanding checkout is crucial for managing your codebase effectively.
### Navigating History
When you use git checkout with a commit hash, Git updates your working directory to match the state of your project at that point in time. For example:
`git checkout ef6f234`

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Git/Checkout/1.png)

This command moves you to a specific commit, placing you in what's called a "detached HEAD" state. Think of this like picking up a historical photograph – you can look at it, but any changes you make won't affect the timeline unless you create a new branch.
When you see "HEAD detached at ef6f234" in your status, it means you're viewing your project at that specific moment in history. This is perfectly safe – you can look around and even make experimental changes without affecting your main work.

### Restoring Individual Files
One of checkout's most powerful features is the ability to restore specific files from different points in history:
`git checkout HEAD file.txt`

This command tells Git: "Replace my current version of file.txt with the version from the most recent commit." You can substitute HEAD with any commit hash or branch name to get that version instead.

### Understanding HEAD
In Git, HEAD is a special pointer that typically refers to your current location in the repository's history. When you're on a branch, HEAD points to the latest commit of that branch. You can use HEAD in several ways:

HEAD: The current commit
HEAD~1: The previous commit
HEAD~2: Two commits ago
And so on...

To reattach a detached head you only need to switch back to the branch master:

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Git/Checkout/2.png)

### Safe File Management
Git checkout provides a safe way to discard unwanted changes in your working directory. For instance:
`git checkout -- forgotten-file.txt`

This command discards any uncommitted changes to forgotten-file.txt, restoring it to match the version in your last commit. Be careful with this command – it permanently removes any uncommitted changes to the specified files.

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Git/Checkout/3.png)

Common Checkout Patterns
Here's how checkout fits into typical Git workflows:
```
Viewing historical versions:
bashCopygit checkout <commit-hash>
git log --oneline  # to see what things looked like then
git checkout master  # to return to the present
```

Remember that git checkout is a versatile command that can help you navigate through your project's history, restore files to previous states, and create new branches. Understanding how to use it effectively gives you powerful control over your codebase's evolution.

## Managing Changes with Git Restore
Git restore is a specialized command introduced to provide clearer, more focused ways to manage changes in your working directory and staging area. While checkout can handle many tasks, restore is specifically designed for managing file changes, making it more straightforward to use and understand.

### Understanding Working Directory Changes

When you modify files in your Git repository, these changes exist in your working directory. Git restore helps you manage these changes in several ways. Think of your changes as existing in three places: your working directory (where you make changes), the staging area (where changes wait to be committed), and the repository itself (where committed changes live).

### Discarding Working Directory Changes
If you've made changes to a file that you want to discard, you can use git restore to return the file to its last committed state:
`git restore boardgames.txt`
This command effectively says "I want to discard my changes and restore this file to how it looked in the last commit." It's like having an "undo" button for your file changes. However, be careful – this operation cannot be undone, as it permanently discards your changes.

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Git/Restore/1.png)

### Managing the Staging Area
Git restore becomes even more powerful when working with the staging area. You can use it to:
```
Unstage changes (move them from staging back to working directory):
git restore --staged boardgames.txt
This command doesn't discard changes; it just moves them back to your working directory.
Stage changes (move them from working directory to staging):
git add boardgames.txt
While git restore handles unstaging, we still use git add for staging changes.
```

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Git/Restore/2.png)

### Understanding Git Reset and Revert
Sometimes you need to undo changes that have already been committed. Git provides two important commands for this:

Git Reset (--hard):
`git reset --hard e819897`
This command forcibly moves your branch pointer to a specific commit, discarding all commits after it. It's like saying "I want to pretend everything after this point never happened." Use this with caution, as it can permanently remove commits.

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Git/Restore/3.png)

Git Revert:
`git revert 0559a7c`
Revert creates a new commit that undoes the changes from a specific commit. It's safer than reset because it doesn't delete history – instead, it adds a new commit that reverses the changes. This is particularly important when working with shared repositories, as it preserves the history of what happened.

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Git/Restore/4.png)


When working with shared repositories, prefer revert over reset, as it maintains a clear history of changes.
If you need to unstage multiple files, you can use wildcards:
`git restore --staged *.txt`

Remember that git restore is for managing changes that haven't been committed yet, while revert and reset handle committed changes.

## Working with Remote Repositories
Remote repositories are copies of your project hosted on the internet or a network somewhere. They enable collaboration with others and provide a backup of your work. GitHub is one of the most popular platforms for hosting remote repositories, but the concepts apply to any Git hosting service.

### Getting Started with Remote Repositories
You can start working with remote repositories by cloning an existing repository:
`ygit clone https://github.com/username/repository-name`

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Git/Remote%20Repos/1.png)

When you clone a repository, Git automatically:
```
Downloads all the code and its entire history
Sets up a remote called "origin" pointing to the source repository
Creates a local branch tracking the remote's main branch
```

### Understanding Remote Connections
You can view your remote connections using:
`git remote -v`
This command shows you the URLs Git uses for reading (fetch) and writing (push) operations. A typical output looks like:
```
Copyorigin  https://github.com/username/repository-name (fetch)
origin  https://github.com/username/repository-name (push)
```

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Git/Remote%20Repos/2.png)

### Authentication with GitHub
When working with GitHub, you'll need to authenticate your requests. Modern Git uses personal access tokens instead of passwords. To set this up:
```
Visit GitHub's Settings → Developer Settings → Personal Access Tokens
Generate a new token with appropriate permissions
Use this token as your password when Git asks for authentication
```

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Git/Remote%20Repos/3.png)

### Basic Remote Operations
The main commands for working with remote repositories are:

Pushing changes:
`git push origin main`

This command sends your committed changes to the remote repository. 

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Git/Remote%20Repos/5.png)

Pulling changes:
`git pull origin main`

This command fetches changes from the remote repository and merges them into your local branch. When you pull, you might see output like:
```
Copyremote: Enumerating objects: 6, done.
remote: Counting objects: 100% (6/6), done.
remote: Compressing objects: 100% (5/5), done.
Unpacking objects: 100% (5/5), done.
```

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Git/Remote%20Repos/7.png)

### Best Practices for Remote Collaboration

- Pull before you start working: This helps avoid merge conflicts by ensuring you have the latest code.
- Push small, logical changes: Rather than making one massive commit, break your work into smaller, meaningful commits.
- Write clear commit messages: Your commits will be visible to others, so make sure your messages clearly explain what changes you made and why.
- Use branches for new features: When working on a team, create feature branches instead of working directly on main


Keep your repository clean: Don't commit unnecessary files like build artifacts or IDE configurations. Use .gitignore to exclude these files.

### Handling Common Remote Issues
Sometimes you might encounter issues when working with remotes:

If your push is rejected, it usually means someone else pushed changes before you. Solve this by:
```
git pull origin main  # get their changes
# resolve any conflicts
git push origin main  # try pushing again
```

If you need to undo a pushed commit, use revert rather than reset:
`git revert <commit-hash>`

Remember that remote repositories are not just for backup—they're the foundation of collaborative development. Good remote repository practices help ensure smooth collaboration with your team and maintain a clean, professional project history.
