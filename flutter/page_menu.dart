import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:time_tracker_flutter/page_search.dart';
import 'package:time_tracker_flutter/page_create_project.dart';
import 'package:time_tracker_flutter/page_create_task.dart';



class PageMenu extends StatefulWidget {
  // late final int id;

  late int parentId;
  PageMenu(this.parentId);

  // @override
  _PageMenuState createState() => _PageMenuState();




}

class _PageMenuState extends State<PageMenu> {

  late int parentId;

  @override
  void initState() {
    super.initState();

    parentId = widget.parentId;

    // id = widget.id; // of PageActivities
    // futureTree = getTree(id);
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        actions: <Widget>[
          IconButton(icon: Icon(Icons.home),
              onPressed: () {
                Navigator.popUntil(context, ModalRoute.withName('/'));
              }
          ),
          IconButton(
              onPressed: () {
              }, icon: Icon(Icons.search))
        ],
      ),
      body: Container (
          child: Center (
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Container(
                    width: 250.0,
                    height: 250,
                    padding: new EdgeInsets.fromLTRB(20.0, 40.0, 20.0, 40.0),
                    color: Colors.grey,
                    child: Column(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          ClipRRect(
                            borderRadius: BorderRadius.circular(4),
                            child: Stack(
                              children: <Widget>[
                                Positioned.fill(
                                  child: Container(
                                    decoration: const BoxDecoration(
                                        color: Colors.black
                                    ),
                                  ),
                                ),
                                TextButton(
                                  style: TextButton.styleFrom(
                                    padding: const EdgeInsets.all(16.0),
                                    primary: Colors.white,
                                    textStyle: const TextStyle(fontSize: 20),
                                  ),
                                  onPressed: () { _onCreateClick("Project", parentId);},
                                  child: const Text('Add new project'),
                                ),
                              ],
                            ),
                          ),
                          ClipRRect(
                            borderRadius: BorderRadius.circular(4),
                            child: Stack(
                              children: <Widget>[
                                Positioned.fill(
                                  child: Container(
                                    decoration: const BoxDecoration(
                                        color: Colors.black
                                    ),
                                  ),
                                ),
                                TextButton(
                                  style: TextButton.styleFrom(
                                    padding: const EdgeInsets.all(16.0),
                                    primary: Colors.white,
                                    textStyle: const TextStyle(fontSize: 20),
                                  ),
                                  onPressed: () {_onCreateClick("Task", parentId);},
                                  child: const Text('Add new task'),
                                ),
                              ],
                            ),
                          ),
                        ]
                    )
                )
              ],
            ),
          )
      )
    );
  }

  void _onCreateClick(String name, int parentId) {
    if (name == "Project")
      {
        print("Parent id: " + parentId.toString());
        Navigator.of(context)
            .push(MaterialPageRoute<void>(builder: (context) => PageCreateProject(parentId)));
      } else if (name == "Task")
      {
        Navigator.of(context)
            .push(MaterialPageRoute<void>(builder: (context) => PageCreateTask(parentId)));
      }
    }
}
