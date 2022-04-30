import Header from "components/Header";
import css from "./index.module.css";
function Dashboard() {
  return (
    <>
      <Header />
      <div className={css.body}>
        <div className={css.profileButtonParent}><span class="material-icons">
manage_accounts
</span>
          <div className={css.profileButton}>
          Profile
          </div>
        </div>
      </div>

    </>
  );
}

export default Dashboard;
