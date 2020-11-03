import React from "react";
import { render } from "@testing-library/react";
import App from "./App";

test("Home Page", () => {
  const Render = render(<App />);
  const linkElement = Render.getByText(/learn react/i);
  expect(linkElement).toBeInTheDocument();
});
