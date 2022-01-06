import 'package:flutter/material.dart';
import 'package:time_tracker_flutter/tree.dart' as Tree hide getTree;
// to avoid collision with an Interval class in another library
import 'package:time_tracker_flutter/requests.dart';

class PageInfo extends StatefulWidget {
  final int id; // final because StatefulWidget is immutable

  PageInfo(this.id);

  @override
  _PageInfoState createState() => _PageInfoState();
}

class _PageInfoState extends State<PageInfo> {
  late int id;
  late Future<Tree.Tree> futureTree;

  @override
  void initState() {
    super.initState();
    id = widget.id;
    futureTree = getTree(id);
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<Tree.Tree>(
      future: futureTree,
      // this makes the tree of children, when available, go into snapshot.data
      builder: (context, snapshot) {
        // anonymous function
          int numChildren = snapshot.data!.root.children.length;
          return Scaffold(
            appBar: AppBar(
              title: Text(snapshot.data!.root.name + " information"),
              actions: <Widget>[
                IconButton(icon: Icon(Icons.home),
                onPressed: () {
                  Navigator.popUntil(context, ModalRoute.withName('/'));
                  }
                ),
              ],
            ),
            body: Container(
              margin: EdgeInsets.only(left: 40),
              child: Center(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    Row(
                      children: [
                        Container(
                            width: 120,
                            child: Text('Start Date: ',
                              style: new TextStyle(
                                fontSize: 20.0,
                                fontWeight: FontWeight.bold,
                              ),
                            )
                        ),
                        Text(snapshot.data!.root.initialDate.toString()),
                      ],
                    ),
                    Row(
                      children: [
                        Container(
                            width: 120,
                            child: Text('Final Date: ',
                              style: new TextStyle(
                                fontSize: 20.0,
                                fontWeight: FontWeight.bold,
                              ),
                            )
                        ),
                        Text(snapshot.data!.root.lastDate.toString()),
                      ],
                    ),
                    Row(
                      children: [
                        Container(
                            width: 120,
                            child: Text('Duration: ',
                              style: new TextStyle(
                                fontSize: 20.0,
                                fontWeight: FontWeight.bold,
                              ),
                            )
                        ),
                        Text(snapshot.data!.root.duration.toString()),
                      ],
                    ),
                    Row(
                      children: [
                        Container(
                            width: 120,
                            child: Text('Tags: ',
                              style: new TextStyle(
                                fontSize: 20.0,
                                fontWeight: FontWeight.bold,
                              ),
                            )
                        ),
                        Text("getTags"),
                      ],
                    ),
                    Row(
                      children: [
                        Container(
                            width: 120,
                            child: Text('Description: ',
                              style: new TextStyle(
                                fontSize: 20.0,
                                fontWeight: FontWeight.bold,
                              ),
                            )
                        ),
                        Text("getDescription"),
                      ],
                    ),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                      ],
                    ),
                  ],
                ),
              ),
            ),
          );
        }
    );
  }
}
