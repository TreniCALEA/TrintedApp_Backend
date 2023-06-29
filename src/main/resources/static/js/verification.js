import { Client, Account } from "appwrite";

const client = new Client()
  .setEndpoint("https://cloud.appwrite.io/v1")
  .setProject("645d4c2c39e030c6f6ba");

const account = new Account(client);

const urlParams = new URLSearchParams(window.location.search);
const userId = urlParams.get("userId");
const secret = urlParams.get("secret");

let promise = account.updateVerification(userId, secret);

promise.then(
  function (response) {
    console.log(response);
    document.getElementById("message").innerHTML = JSON.stringify(response);
    window.location.replace(
      "appwrite-callback-645d4c2c39e030c6f6ba://verified"
    );
  },
  function (error) {
    console.log(error);
  }
);
