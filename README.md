# ECM2425-The-Daily-Forecast

# **The Daily Forecast - Android App**

## **1. Introduction**

**The Daily Forecast** is a simple Android app designed to provide users with a combination of weather forecasts, news updates, and finance tracking in a convenient, easy-to-use interface. The app is structured into three main sections: Weather, News, and Finance, each offering key functionalities to keep users informed and manage their personal finances.

The app allows users to:
- View current weather information and a 7-day forecast.
- Stay updated with news articles relevant to their interests.
- Track finances and manage budgets with user-friendly features.

This README provides an overview of the app’s purpose, key features, design rationale, and instructions on how to run the app.

---

## **2. Design Rationale**

The app is built using **Kotlin** and leverages Android's core features, including **Fragments** for navigation, **RecyclerView** for displaying lists, and **SharedPreferences** for storing user preferences.

### **Key Design Decisions:**

1. **Activities vs. Fragments**:
   - I used **Fragments** for navigation between the Weather, News, and Finance sections. This approach promotes modularity and easier management of UI components, as well as better memory management when switching between views.
   - The app follows **Jetpack Navigation** for handling fragment transitions, ensuring a smooth user experience.

2. **Layouts and Views**:
   - The app uses **LinearLayout**, **ConstraintLayout**, and **RecyclerView** to create a clean and flexible UI. The layouts are optimized for different screen sizes and orientations to ensure responsiveness.

3. **Data Storage**:
   - A **Room Database** is used to persistently store added incomes, expenses and categories they may add for each
   - **SharedPreferences** is used to store user preferences, such as their preferred unit of currency.
   - I used **RecyclerView** to display dynamic lists of weather data, news articles, and finance transactions, allowing efficient handling of large data sets.

5. **Themes**:
   - The app features both **Light Mode** and **Dark Mode** themes. The UI adjusts automatically based on the user's system preferences to provide an optimal experience in both modes.

---

## **3. Novel Features**

### **Weather Section:**
- Users can view detailed weather information, including current temperature, humidity, and a 7-day forecast.
- The weather icons change dynamically based on the forecast (e.g., sun, clouds, rain).

### **News Section:**
- News is fetched dynamically from a REST API and displayed in a **RecyclerView** with clickable items. Users can read detailed articles with an intent opening the browser app of thier choice.

### **Finance Section:**
- Users can manage their finances by adding transactions, viewing their spending history, and seeing their monthly balance.
- The app calculates the total balance dynamically based on user input.

### **User Preferences:**
- The app allows users to set and store their preferences using **SharedPreferences**.

---

## **4. Challenges and Future Improvements**

### **Challenges Encountered:**
1. **Managing RecyclerView Data**:
   - One challenge was efficiently managing large data sets in **RecyclerView** while ensuring smooth scrolling. I addressed this by optimizing the adapter and using `ViewHolder` patterns effectively.
   
2. **Handling API Calls for Weather and News**:
   - Initially, integrating APIs for weather and news data was challenging due to inconsistent data formats. I handled this by writing custom data parsers and error handling.
   - Due to needing to work with these API's, I decided to migrate the application to use kotlin.
  

### **Future Improvements:**
1. **Better Error Handling**:
   - Currently, the app handles basic errors (e.g., no internet connection), but I plan to implement more robust error handling for API failures.
   
2. **Offline Mode**:
   - In future versions, I aim to implement offline support so that users can still access cached weather or news data when there’s no internet connection.

3. **Additional Features**:
   - **Weather Maps**: Display interactive weather maps with radar imagery.
   - **Currency Conversion**: Integrate a currency conversion feature in the Finance section.


---

## **5. How to Use**

1. **Clone the repository**:
   - Clone the repository to your local machine.
   - Open the project in **Android Studio**.

2. **Run the app**:
   - Ensure you have an Android Emulator running or a physical device connected.
   - Select the desired device and click on the **Run** button.

3. **Navigation**:
   - The app opens on the **Home** screen, where users can navigate to the **Weather**, **News**, and **Finance** sections using the bottom navigation menu.

4. **User Preferences**:
   - In the **Settings** section, users can modify their preferences, such as currency.

---

## **6. Project Structure**

The project is organized as follows:

- **`app/src/main/java/com/edwardpratt/thedailyforecast/`**: Contains Kotlin code for app activities, fragments, and utilities.
- **`app/src/main/res/`**: Contains the app’s resources, including layouts, drawables, and strings.
- **`app/src/main/AndroidManifest.xml`**: Defines the app's components and permissions.

---

## **7. Dependencies**

The app uses the following libraries:
- **Jetpack Navigation** for fragment management.
- **Retrofit** for API calls (to fetch weather and news data).
- **Glide** for image loading (e.g., weather icons).
- **RecyclerView** for displaying lists.

All dependencies are included in the **`build.gradle`** file.

---

## **8. License**

This project is open-source under the MIT License.

---
