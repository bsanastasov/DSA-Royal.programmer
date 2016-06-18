#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <unistd.h>

int main(int argc, char* argv[]) {

	int status, fd1;
	pid_t cpid;
	int pipefd[2];
	char c;

	if (argc != 2) {
		write(2, "Wrong number of args\n", 23);
		exit(1);
	}

	if (pipe(pipefd) == -1) {
		write(2, "error pipe\n", 13);
		exit(1);
	}
	cpid = fork();
	if (cpid == -1) {
		write(2, "error fork\n", 13);
		exit(1);
	}

	if (cpid > 0) {
		wait(&status);
		close(pipefd[0]);
		dup2(pipefd[1], STDIN_FILENO);
		close(pipefd[1]);

		if ((fd1 = open(argv[1], O_RDONLY, 0644)) == -1){
        	write(2, "Cannot open file\n", 17);
            exit(-1);
         }

         while (read(fd1, &c, 1)) {
         	if (execlp("sort", "sort",argv[1], 0) == -1) {
				write(2, "Error sort\n", 13);
				exit(-1);
			}
         }

	}
	else {
		close(pipefd[1]);
		dup2(pipefd[0], STDIN_FILENO);
		close(pipefd[0]);
		if (execlp("cat", "cat", argv[1], 0) == -1) {
			write(2, "Error cat\n", 13);
			exit(-1);
		}
	}
}