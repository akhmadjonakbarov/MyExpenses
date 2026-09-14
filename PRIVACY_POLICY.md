# Privacy Policy — MyExpenses

_Last updated: September 14, 2026_

MyExpenses ("the App") is an expense-tracking application provided by the developer ("we", "us"). This policy explains what data the App collects, how it is used, and the choices you have.

## 1. Information we collect

The App collects the following data, only as needed to provide its core functionality:

- **Account information** when you sign in: your email address and display name (provided by you or by your chosen sign-in provider, such as Google).
- **User-generated content**: the expense/income transactions, balances and debt records you create inside the App.
- **Diagnostic information**: crash reports (via Firebase Crashlytics) and anonymous usage analytics (via Firebase Analytics) such as app open/close events, screen views and in-app events. Analytics data does not include your transaction contents.

## 2. How we use your data

- Your account and transaction data are used solely to authenticate you and to sync your records across devices and app reinstalls.
- Crash and analytics data are used to improve stability, fix bugs and understand feature usage.

## 3. Where and how data is stored

- Account authentication is provided by **Firebase Authentication**.
- Your transactions and balances are stored in **Google Cloud Firestore** under your unique user ID.
- All cloud storage is provided by Google through Firebase and is subject to Google's security infrastructure.

## 4. Sharing

We do **not** sell, rent, or trade your personal data. Data is only processed by the service providers required to operate the App (Google/Firebase). We do not send your data to advertisers.

## 5. Your choices and rights

- **Local data**: You may delete transactions at any time inside the App.
- **Cloud data**: Your synced data is cleared from your account's Firestore storage (when you sign out, the app clears local data; to fully erase stored data, request account deletion via the contact below).
- **Delete your account**: To request deletion of your account and all associated stored data, contact us using the email below. We will process the request within 30 days.

## 6. Children's privacy

The App is not intended for children under 13, and we do not knowingly collect data from them.

## 7. Changes to this policy

We may update this policy from time to time. Continued use of the App after changes constitutes acceptance of the revised policy.

## 8. Contact

For privacy questions or account/data deletion requests, contact:
[your-email@example.com]

---

### Play Console setup reminder
1. Host this document publicly (e.g., a GitHub repository page or any URL) and paste that URL into the **App content → Privacy policy** section of Play Console.
2. Complete the **Data safety** form in Play Console. Suggested answers:
   - Data shared: none except via service provider (Google sign-in).
   - Data collected: email address, name, app diagnostics (crash logs, usage analytics), user-created content (transactions).
   - Encryption in transit: yes. Optional deletion: yes.
3. Declare the declared permissions and confirm **Google Sign-In** usage to Google Play as part of App Access.