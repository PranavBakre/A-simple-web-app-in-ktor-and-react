import css from "./index.module.css";

function TextField({
  groupClassName,
  groupStyle,
  name,
  labelText,
  labelClassName,
  labelStyle,
  type,
  placeholder,
  inputClassName,
  inputStyle,
  value
}) {
  return (
    <div
      className={`${css.inputGroup} ${groupClassName ?? ""}`}
      {...(groupStyle && { style: groupStyle })}
    >
      <label
        htmlFor={name}
        className={`${css.label} ${labelClassName ?? ""}`}
        {...(labelStyle && { style: labelStyle })}
      >
        {labelText}
      </label>
      <input
        id={name}
        type={type}
        placeholder={placeholder}
        className={`${css.input} ${inputClassName ?? ""}`}
        {...(inputStyle && { style: inputStyle })}
        value={value}
      />
    </div>
  );
}
export default TextField;
