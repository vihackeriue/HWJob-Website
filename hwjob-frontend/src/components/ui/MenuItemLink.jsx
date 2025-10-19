import classNames from "classnames";
import React from "react";
import { Link, useLocation } from "react-router-dom";
import { tGlobal } from "../../utils/translator";

const MenuItemLink = ({
  item,
  activeClass = "text-brightOrange font-semibold",
  noActiveClass = "",
  baseClass = "px-3 py-2 hover:text-brightOrange flex items-center gap-2",
  isTitle = true,
}) => {
  const { pathname } = useLocation();
  const isActive = pathname === item.path;

  return (
    <Link
      to={item.path}
      className={classNames(baseClass, isActive ? activeClass : noActiveClass)}
    >
      {item.icon && <span className="text-lg">{item.icon}</span>}
      {isTitle && <span>{tGlobal(item.label)}</span>}
    </Link>
  );
};

export default MenuItemLink;
