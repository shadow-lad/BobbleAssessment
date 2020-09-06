# BigText

## Android Assigment for Internship Opportunity at Bobble.ai

### Task 1 Android App

<hr>

Requirements:

- Android SDK
- JDK 1.8

Steps to run app:

1. Connect an android phone or start an android emulator
2. Make sure USB Debugging is on under Developer Options
3. Open the BigText android project in Android Studio or Command Line/Terminal
4. To run using Android Studio, wait for the project to load up and press the green play button on the top right corner and that's it.
5. To run using the Windows Command Line/Terminal, first install the app by typing this in the project root directory:

   For Windows:

   ```sh
   gradlew installDebug
   ```

   For Linux / Mac:

   ```sh
   ./gradlew installDebug
   ```

6. Next, find the app on your device/emulator and open it like you would any other app.

### Task 2 Debugging Assigment

The simplest way to recreate a **java.util.ConcurrentModificationException** is to execute the following code:

```java
import java.util.*;
public class Bobble {

	public static void main(String args[]) {

		//Let's say we have a list of Strings
		List<String> list = new ArrayList<>();

		list.add("Hello");
		list.add("World");
		list.add("Good");
		list.add("Morning");

		// Now while travering the string using the for-each loop,
		for (String s : list) {
			// if we try to remove a value from the list like so,
			list.remove(s);
		}

		// This throws a java.util.ConcurrentModificationException

	}

}
```

Two ways can be enlisted to avoid it from happening:

1. Classic for loop

```java
import java.util.*;
public class Bobble {

	public static void main(String args[]) {

		//Let's say we have a list of Strings
		List<String> list = new ArrayList<>();

		list.add("Hello");
		list.add("World");
		list.add("Good");
		list.add("Morning");

		// This does not throw a java.util.ConcurrentModificationException
		for (int i = list.size() - 1; i >= 0; i-- ) {
			String s = list.get(i);
			list.remove(s);
		}

	}

}
```

2. Iterator

```java
import java.util.*;
public class Bobble {

	public static void main(String args[]) {

		//Let's say we have a list of Strings
		List<String> list = new ArrayList<>();

		list.add("Hello");
		list.add("World");
		list.add("Good");
		list.add("Morning");

		// This does not throw a java.util.ConcurrentModificationException
		for(Iterator<String> itr = list.iterator(); itr.hasNext();){
			/* String s = itr.next();
			list.remove(s); */ // wrong again
			itr.remove(); // right call
		}

	}

}
```
