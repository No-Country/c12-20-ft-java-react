/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,jsx}",
  ],
  plugins: [require("daisyui")],
  theme: {
    extend: {}
  },
  daisyui: {
    themes: [
      {
      },
    ],
  },
}