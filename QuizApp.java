import java.util.Scanner;
import java.util.concurrent.*;

public class QuizApp {
    private String[] questions = {
        "What is the capital of France?",
        "Who wrote 'Romeo and Juliet'?",
        "What is the largest planet in our Solar System?",
        "Which element has the chemical symbol 'O'?",
        "What is the smallest prime number?"
    };

    private String[][] options = {
        {"1. Paris", "2. London", "3. Rome", "4. Berlin"},
        {"1. Charles Dickens", "2. Jane Austen", "3. William Shakespeare", "4. Mark Twain"},
        {"1. Earth", "2. Jupiter", "3. Mars", "4. Saturn"},
        {"1. Gold", "2. Oxygen", "3. Silver", "4. Hydrogen"},
        {"1. 0", "2. 1", "3. 2", "4. 3"}
    };

    private int[] correctAnswers = {1, 3, 2, 2, 3};
    private int score = 0;
    private int[] userAnswers;
    private Scanner scanner;

    public QuizApp() {
        scanner = new Scanner(System.in);
        userAnswers = new int[questions.length];
    }

    public void startQuiz() {
        for (int i = 0; i < questions.length; i++) {
            System.out.println("\nQuestion " + (i + 1) + ": " + questions[i]);
            for (String option : options[i]) {
                System.out.println(option);
            }

            int userAnswer = getUserAnswerWithTimeout();
            userAnswers[i] = userAnswer;

            if (userAnswer == correctAnswers[i]) {
                score++;
            }
        }
        displayResults();
    }

    private int getUserAnswerWithTimeout() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Integer> future = executor.submit(() -> {
            System.out.print("Enter your answer (1-4): ");
            return scanner.nextInt();
        });

        try {
            return future.get(10, TimeUnit.SECONDS); // 10-second timer for each question
        } catch (TimeoutException e) {
            System.out.println("\nTime is up! Moving to the next question.");
            return -1; // Indicating that time has expired
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            executor.shutdownNow();
        }
    }

    private void displayResults() {
        System.out.println("\nQuiz Over! Here are your results:");
        for (int i = 0; i < questions.length; i++) {
            System.out.println("Question " + (i + 1) + ": " + questions[i]);
            System.out.println("Your answer: " + (userAnswers[i] == -1 ? "No answer" : userAnswers[i]));
            System.out.println("Correct answer: " + correctAnswers[i]);
            System.out.println(userAnswers[i] == correctAnswers[i] ? "Correct!" : "Incorrect.");
        }
        System.out.println("\nYour final score is: " + score + "/" + questions.length);
    }

    public static void main(String[] args) {
        QuizApp quizApp = new QuizApp();
        quizApp.startQuiz();
    }
}
